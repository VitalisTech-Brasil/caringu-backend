package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aula.Request.AtribuicaoTreinosAulaRequestPostDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AtribuicaoTreinosAulaItemDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AtribuicaoTreinosAulaResponsePostDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.exception.Aula.AulaNaoEncontradaException;
import tech.vitalis.caringu.exception.PlanoContratado.AlunoSemPlanoContratadoException;
import tech.vitalis.caringu.exception.PlanoContratado.PlanoNaoPertenceAoAlunoException;
import tech.vitalis.caringu.exception.Treino.TreinoNaoEncontradoException;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.AulaTreinoExercicioRepository;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;

import java.util.List;

@Service
public class AulaTreinoExercicioService {

    private final AulaRepository aulaRepository;
    private final AulaTreinoExercicioRepository aulaTreinoExercicioRepository;
    private final PlanoContratadoRepository planoContratadoRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;

    public AulaTreinoExercicioService(
            AulaRepository aulaRepository,
            AulaTreinoExercicioRepository aulaTreinoExercicioRepository,
            PlanoContratadoRepository planoContratadoRepository,
            TreinoExercicioRepository treinoExercicioRepository
    ) {
        this.aulaRepository = aulaRepository;
        this.aulaTreinoExercicioRepository = aulaTreinoExercicioRepository;
        this.planoContratadoRepository = planoContratadoRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
    }

    public AtribuicaoTreinosAulaResponsePostDTO atribuirTreinoAAula(
            AtribuicaoTreinosAulaRequestPostDTO requestDTO
    ) {
        List<AtribuicaoTreinosAulaItemDTO> itensResponse = requestDTO.aulasTreinos().stream().map(item -> {

            // 1. Validar plano ativo
            PlanoContratado planoContratado = planoContratadoRepository
                    .findFirstByAlunoIdAndStatus(item.idAluno(), StatusEnum.ATIVO)
                    .orElseThrow(() -> new AlunoSemPlanoContratadoException("Aluno sem plano ativo."));

            if (!planoContratado.getId().equals(item.idPlanoContratado())) {
                throw new PlanoNaoPertenceAoAlunoException("Plano informado não pertence ao aluno.");
            }

            // 2. Buscar aula rascunho e alterar para status AGENDADO
            Aula aula = aulaRepository.findByPlanoContratadoIdAndDataHorarioInicioAndDataHorarioFimAndStatus(
                    item.idPlanoContratado(),
                    item.dataHorarioInicio(),
                    item.dataHorarioFim(),
                    AulaStatusEnum.RASCUNHO
            ).orElseThrow(() -> new AulaNaoEncontradaException("Aula rascunho não encontrada."));

            // 2.1 Atualizar status da aula
            aula.setStatus(AulaStatusEnum.AGENDADO);
            aulaRepository.save(aula);

            // 3. Buscar treino
            TreinoExercicio treinoExercicio = treinoExercicioRepository.findById(item.idTreinoExercicio())
                    .orElseThrow(() -> new TreinoNaoEncontradoException("Treino não encontrado."));

            // 4. Associar
            AulaTreinoExercicio aulaTreinoExercicio = new AulaTreinoExercicio();

            // 4.1 Calcular ordem
            Integer proximaOrdem = aulaTreinoExercicioRepository.findMaxOrdemByAulaId(aula.getId()) + 1;

            // 5 Salvar aula treino exercício
            aulaTreinoExercicio.setAula(aula);
            aulaTreinoExercicio.setTreinoExercicio(treinoExercicio);
            aulaTreinoExercicio.setOrdem(proximaOrdem);
            aulaTreinoExercicio.setCarga(treinoExercicio.getCarga());
            aulaTreinoExercicio.setRepeticoes(treinoExercicio.getRepeticoes());
            aulaTreinoExercicio.setSeries(treinoExercicio.getSeries());
            aulaTreinoExercicio.setDescanso(treinoExercicio.getDescanso());

            aulaTreinoExercicioRepository.save(aulaTreinoExercicio);

            return new AtribuicaoTreinosAulaItemDTO(
                    item.idAluno(),
                    aula.getId(),
                    item.idPlanoContratado(),
                    item.dataHorarioInicio(),
                    item.dataHorarioFim()
            );
        }).toList();

        return new AtribuicaoTreinosAulaResponsePostDTO(itensResponse);
    }
}
