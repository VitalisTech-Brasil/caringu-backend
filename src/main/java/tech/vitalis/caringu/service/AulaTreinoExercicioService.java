package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AcompanhamentoAulaCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoModeloCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.AtribuicaoTreinosAulaRequestPostDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.AtribuicaoTreinosAulaTreinoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.HorarioAulaDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.RemarcarAulaTreinoRequestDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.*;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.TreinoDetalhadoRepositoryDTO;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.exception.Aula.AulaNaoEncontradaException;
import tech.vitalis.caringu.exception.PlanoContratado.AlunoSemPlanoContratadoException;
import tech.vitalis.caringu.exception.PlanoContratado.PlanoNaoPertenceAoAlunoException;
import tech.vitalis.caringu.exception.Treino.TreinoNaoEncontradoException;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.mapper.AulaTreinoExercicioMapper;
import tech.vitalis.caringu.mapper.ExecucaoExercicioMapper;
import tech.vitalis.caringu.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AulaTreinoExercicioService {

    private final AulaRepository aulaRepository;
    private final AulaTreinoExercicioMapper aulaTreinoExercicioMapper;
    private final ExecucaoExercicioMapper execucaoExercicioMapper;
    private final ExecucaoExercicioRepository execucaoExercicioRepository;
    private final AulaTreinoExercicioRepository aulaTreinoExercicioRepository;
    private final PlanoContratadoRepository planoContratadoRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;
    private final TreinoRepository treinoRepository;

    public AulaTreinoExercicioService(
            AulaRepository aulaRepository,
            AulaTreinoExercicioMapper aulaTreinoExercicioMapper,
            ExecucaoExercicioMapper execucaoExercicioMapper,
            ExecucaoExercicioRepository execucaoExercicioRepository,
            AulaTreinoExercicioRepository aulaTreinoExercicioRepository,
            PlanoContratadoRepository planoContratadoRepository,
            TreinoExercicioRepository treinoExercicioRepository,
            TreinoRepository treinoRepository
    ) {
        this.aulaRepository = aulaRepository;
        this.aulaTreinoExercicioMapper = aulaTreinoExercicioMapper;
        this.execucaoExercicioMapper = execucaoExercicioMapper;
        this.execucaoExercicioRepository = execucaoExercicioRepository;
        this.aulaTreinoExercicioRepository = aulaTreinoExercicioRepository;
        this.planoContratadoRepository = planoContratadoRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.treinoRepository = treinoRepository;
    }

    public VisualizarAulasResponseDTO listarAulasComTreinosExercicios(Integer idAula, Integer idAluno) {
        List<TreinoDetalhadoRepositoryDTO> aulaComTreinosExercicios = aulaTreinoExercicioRepository
                .listarAulasComTreinosExercicios(idAula, idAluno);

        if (aulaComTreinosExercicios.isEmpty()) {
            throw new AulaNaoEncontradaException("Aula não encontrada para o aluno informado");
        }

        TreinoDetalhadoRepositoryDTO primeiro = aulaComTreinosExercicios.getFirst();

        List<VisualizarAulasItemResponseDTO> exercicios = aulaComTreinosExercicios.stream()
                .map(d -> new VisualizarAulasItemResponseDTO(
                        d.idAula(),
                        d.idExecucaoExercicio(),
                        d.nomeExercicio(),
                        d.carga(),
                        formatarDuracao(d.descanso()),
                        d.repeticoesSeries(),
                        d.grupoMuscular().getValue(),
                        d.observacoes(),
                        d.urlVideoExecucao(),
                        d.aulaRealizada()
                ))
                .toList();

        return new VisualizarAulasResponseDTO(
                primeiro.idTreino(),
                primeiro.nomeTreino(),
                exercicios
        );
    }

    public List<AulaComTreinoDTO> listarProximasAulas(Integer idAluno) {

        final int QUANTIDADE_AULAS_EXIBIDAS = 2;

        List<AulaComTreinoModeloCruDTO> listaCrua = aulaTreinoExercicioRepository
                .listarProximasAulas(idAluno);

        List<AulaComTreinoDTO> aulasAgrupadas = aulaTreinoExercicioMapper
                .toAulaComTreinoDTO(listaCrua);

        return aulasAgrupadas.stream()
                .limit(QUANTIDADE_AULAS_EXIBIDAS)
                .toList();
    }

    public AcompanhamentoAulaResponseDTO listarAcompanhamentoDaAula(Integer idAula) {

        List<AcompanhamentoAulaCruDTO> linhas = aulaTreinoExercicioRepository.listarAcompanharDaAula(idAula);
        return aulaTreinoExercicioMapper.toAcompanhamentoAulaResponseDTO(linhas);
    }

    @Transactional
    public AtribuicaoTreinosAulaResponsePostDTO atribuirTreinoAAula(
            AtribuicaoTreinosAulaRequestPostDTO requestDTO
    ) {
        List<AtribuicaoTreinosAulaTreinoResponseDTO> itensResponse = new ArrayList<>();

        for (AtribuicaoTreinosAulaTreinoDTO item : requestDTO.aulasTreinos()) {

            // 1. Validar plano ativo
            PlanoContratadoEntity planoContratado = planoContratadoRepository
                    .findFirstByAlunoIdAndStatus(item.idAluno(), StatusEnum.ATIVO)
                    .orElseThrow(() -> new AlunoSemPlanoContratadoException("Aluno sem plano ativo."));

            if (!planoContratado.getId().equals(item.idPlanoContratado())) {
                throw new PlanoNaoPertenceAoAlunoException("Plano informado não pertence ao aluno.");
            }

            // 2. Buscar exercícios do treino
            List<TreinoExercicio> treinoExercicios = treinoExercicioRepository.findByTreino_Id(item.idTreino());
            if (treinoExercicios.isEmpty()) {
                throw new TreinoNaoEncontradoException("Treino não encontrado.");
            }

            // Lista de aulas criadas (vai dentro do response agrupado)
            List<AulaCriadaDTO> aulasCriadas = new ArrayList<>();

            // 3. Iterar pelas aulas informadas no payload
            for (HorarioAulaDTO horario : item.listaHorariosAula()) {

                // 3.1 Buscar aula rascunho
                Aula aula = aulaRepository.findByPlanoContratadoIdAndDataHorarioInicioAndDataHorarioFimAndStatus(
                        item.idPlanoContratado(),
                        horario.dataHorarioInicio(),
                        horario.dataHorarioFim(),
                        AulaStatusEnum.RASCUNHO
                ).orElseThrow(() -> new AulaNaoEncontradaException("Aula rascunho não encontrada."));

                // 3.2 Atualizar status
                aula.setStatus(AulaStatusEnum.AGENDADO);
                aulaRepository.save(aula);

                // 3.3 Criar AulaTreinoExercicio para cada exercício do treino
                for (TreinoExercicio te : treinoExercicios) {
                    Integer proximaOrdem = aulaTreinoExercicioRepository
                            .findMaxOrdemByAulaId(aula.getId()) + 1;

                    AulaTreinoExercicio ate = new AulaTreinoExercicio();
                    ate.setAula(aula);
                    ate.setTreinoExercicio(te);
                    ate.setOrdem(proximaOrdem);
                    ate.setCarga(te.getCarga());
                    ate.setRepeticoes(te.getRepeticoes());
                    ate.setSeries(te.getSeries());
                    ate.setDescanso(te.getDescanso());

                    aulaTreinoExercicioRepository.save(ate);

                    // Criando uma execução vinculada
                    ExecucaoExercicio execucao = new ExecucaoExercicio();
                    execucao.setAulaTreinoExercicio(ate);
                    execucao.setCargaExecutada(te.getCarga());
                    execucao.setRepeticoesExecutadas(te.getRepeticoes());
                    execucao.setSeriesExecutadas(te.getSeries());
                    execucao.setDescansoExecutado(te.getDescanso());
                    execucao.setFinalizado(false);

                    execucaoExercicioRepository.save(execucao);
                }

                // 3.4 Adicionar ao agrupamento de aulasCriadas
                aulasCriadas.add(new AulaCriadaDTO(
                        aula.getId(),
                        aula.getDataHorarioInicio(),
                        aula.getDataHorarioFim()
                ));
            }

            // 4. Montar o agrupamento do response
            itensResponse.add(new AtribuicaoTreinosAulaTreinoResponseDTO(
                    item.idAluno(),
                    item.idPlanoContratado(),
                    item.idTreino(),
                    aulasCriadas
            ));
        }

        return new AtribuicaoTreinosAulaResponsePostDTO(itensResponse);
    }

    @Transactional
    public RemarcarAulaTreinoResponseDTO remarcarAulaTreino(RemarcarAulaTreinoRequestDTO request) {
        // 1. Buscar aula
        Aula aula = aulaRepository.findById(request.idAula())
                .orElseThrow(() -> new AulaNaoEncontradaException("Aula não encontrada"));

        // 2. Atualizar horário
        aula.setDataHorarioInicio(request.novoHorarioInicio());
        aula.setDataHorarioFim(request.novoHorarioFim());
        aula.setStatus(AulaStatusEnum.REAGENDADO);
        aulaRepository.save(aula);

        // 3. Validar treino novo
        Treino treinoNovo = treinoRepository.findByIdComPersonal(request.idTreinoNovo())
                .orElseThrow(() -> new TreinoNaoEncontradoException("Treino novo não encontrado"));

        // 4. Apagar execuções e vínculos antigos
        List<AulaTreinoExercicio> antigos = aulaTreinoExercicioRepository.findByAulaId(aula.getId());
        if (!antigos.isEmpty()) {
            List<Integer> idsAntigos = antigos.stream().map(AulaTreinoExercicio::getId).toList();
            execucaoExercicioRepository.deleteByAulaTreinoExercicioIdIn(idsAntigos);
            aulaTreinoExercicioRepository.deleteAll(antigos);
            aulaTreinoExercicioRepository.flush(); // força envio imediato do delete
        }

        // 5. Criar vínculos com treino novo
        List<TreinoExercicio> exercicios = treinoExercicioRepository.findByTreinoId(treinoNovo.getId());
        AtomicInteger ordemCounter = new AtomicInteger(1);

        List<AulaTreinoExercicio> novos = exercicios.stream()
                .map(ex -> aulaTreinoExercicioMapper.toEntity(aula, ex, ordemCounter.getAndIncrement()))
                .toList();

        List<AulaTreinoExercicio> salvos = aulaTreinoExercicioRepository.saveAll(novos);

        // 6. Criar execuções “em branco”
        List<ExecucaoExercicio> execucoes = salvos.stream()
                .map(execucaoExercicioMapper::toNovaExecucao)
                .toList();
        execucaoExercicioRepository.saveAll(execucoes);

        // 7. Resposta
        List<AulaTreinoExercicioRemarcarAulaResponseDTO> exerciciosDTO =
                salvos.stream().map(aulaTreinoExercicioMapper::toResponse).toList();

        return new RemarcarAulaTreinoResponseDTO(
                aula.getId(),
                treinoNovo.getId(),
                aula.getDataHorarioInicio(),
                aula.getDataHorarioFim(),
                exerciciosDTO
        );
    }

    private String formatarDuracao(int segundos) {
        int minutos = segundos / 60;
        int restoSegundos = segundos % 60;

        if (minutos > 0 && restoSegundos > 0) {
            return String.format("%d minuto%s e %d segundo%s",
                    minutos, minutos > 1 ? "s" : "",
                    restoSegundos, restoSegundos > 1 ? "s" : "");
        } else if (minutos > 0) {
            return String.format("%d minuto%s", minutos, minutos > 1 ? "s" : "");
        } else {
            return String.format("%d segundo%s", restoSegundos, restoSegundos > 1 ? "s" : "");
        }
    }
}
