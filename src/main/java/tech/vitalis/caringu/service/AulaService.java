package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulaRascunhoResponseGetDTO;
import tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulasRascunhoResponseDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoItemDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoRequestPostDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulaRascunhoCriadaDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulaRascunhoResponsePostDTO;
import tech.vitalis.caringu.dtos.Aula.TotalAulasAgendamentoResponseGetDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.*;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.exception.Aula.AulaConflitanteException;
import tech.vitalis.caringu.exception.PlanoContratado.AlunoSemPlanoContratadoException;
import tech.vitalis.caringu.exception.SessaoTreino.SessaoTreinoNaoEncontradoException;
import tech.vitalis.caringu.mapper.AulaMapper;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.strategy.SessaoTreino.StatusSessaoTreinoValidationStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final PlanoContratadoRepository planoContratadoRepository;
    private final AulaMapper aulaMapper;

    public AulaService(
            AulaRepository aulaRepository,
            PlanoContratadoRepository planoContratadoRepository,
            AulaMapper aulaMapper
    ) {
        this.aulaRepository = aulaRepository;
        this.planoContratadoRepository = planoContratadoRepository;
        this.aulaMapper = aulaMapper;
    }

    public List<SessaoAulasAgendadasResponseDTO> listarAulasPorPersonal(Integer idPersonal) {
        return aulaRepository.findAllAulasPorPersonal(idPersonal);
    }

    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(Integer idAluno, Integer idExercicio) {
        return aulaRepository.buscarEvolucaoCarga(idExercicio, idAluno);
    }

    public List<EvolucaoTreinoCumpridoResponseDTO> buscarEvolucaoTreinosCumpridosMensal(Integer alunoId, Integer exercicioId) {
        return aulaRepository.buscarEvolucaoTreinosCumpridosMensal(alunoId, exercicioId);
    }

    public HorasTreinadasResponseDTO buscarHorasTreinadas(Integer idAluno, Integer idExercicio) {
        List<Object[]> resultados = aulaRepository.buscarHorasAgrupadasPorAlunoExercicio(idAluno, idExercicio);

        List<HorasTreinadasSemanaMesDTO> dados = resultados.stream()
                .map(r -> new HorasTreinadasSemanaMesDTO(
                        (Integer) r[0],
                        (String) r[1],
                        (Integer) r[2],
                        (String) r[3],
                        ((Number) r[4]).intValue(),
                        ((Number) r[5]).intValue(),
                        ((Number) r[6]).intValue(),
                        ((Number) r[7]).doubleValue()
                ))
                .collect(Collectors.toList());

        return new HorasTreinadasResponseDTO(idAluno, idExercicio, dados);
    }

    public TotalAulasAgendamentoResponseGetDTO buscarDisponibilidadeDeAulas(Integer idAluno) {
        return aulaRepository.buscarDisponibilidadeDeAulas(idAluno);
    }

    public AulasRascunhoResponseDTO buscarAulasRascunho(Integer idAluno) {
        List<AulaRascunhoResponseGetDTO> aulas = aulaRepository.buscarAulasRascunho(idAluno);
        return new AulasRascunhoResponseDTO(!aulas.isEmpty(), aulas);
    }

    public AulaRascunhoResponsePostDTO criarAulasRascunho(
            Integer idAluno,
            AulaRascunhoRequestPostDTO requestDTO
    ) {

        // 1. Buscar plano ativo do aluno
        PlanoContratado planoAtivo = planoContratadoRepository
                .findFirstByAlunoIdAndStatus(idAluno, StatusEnum.ATIVO)
                .orElseThrow(() -> new AlunoSemPlanoContratadoException("Aluno não possui plano contratado ativo."));

        // 1.1 Validar se as datas e horários de início e fim, já tem alguma aula nesse período
        for (AulaRascunhoItemDTO dto : requestDTO.aulas()) {
            List<Aula> aulasConflitantes = aulaRepository.findAulasNoPeriodo(
                    planoAtivo.getId(),
                    dto.dataHorarioInicio(),
                    dto.dataHorarioFim()
            );
            if (!aulasConflitantes.isEmpty()) {
                throw new AulaConflitanteException(
                        "Já existe uma aula agendada no período: "
                                + dto.dataHorarioInicio() + " - " + dto.dataHorarioFim()
                );
            }
        }

        // 2. Converter DTOs para entidades
        List<Aula> aulas = requestDTO.aulas().stream()
                .map(dto -> aulaMapper.toEntity(dto, planoAtivo))
                .toList();

        // 3. Salvar todas
        List<Aula> aulasSalvas = aulaRepository.saveAll(aulas);

        // 4. Converter para resposta
        List<AulaRascunhoCriadaDTO> aulasCriadas = aulasSalvas.stream()
                .map(aulaMapper::toResponse)
                .toList();

        return new AulaRascunhoResponsePostDTO(aulasCriadas);
    }

    public void atualizarStatus(Integer idSessaoTreino, AulaStatusEnum novoStatus) {

        Aula aula = aulaRepository.findById(idSessaoTreino)
                .orElseThrow(() -> new SessaoTreinoNaoEncontradoException("Sessão treino com id %d não encontrado.".formatted(idSessaoTreino)));

        validarEnums(Map.of(
                new StatusSessaoTreinoValidationStrategy(), novoStatus
        ));

        if (novoStatus.equals(AulaStatusEnum.REALIZADO)) {
            aula.setDataHorarioFim(LocalDateTime.now());
        }

        if (novoStatus.equals(AulaStatusEnum.AGENDADO)) {
            aula.setDataHorarioFim(null);
        }

        aula.setStatus(novoStatus);
        aulaRepository.save(aula);
    }
}
