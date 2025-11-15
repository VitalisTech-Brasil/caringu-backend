package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulaRascunhoResponseGetDTO;
import tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulasRascunhoResponseDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoItemDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoRequestPostDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulasAlunoRequestDTO;
import tech.vitalis.caringu.dtos.Aula.Response.*;
import tech.vitalis.caringu.dtos.Aula.TotalAulasAgendamentoResponseGetDTO;
import tech.vitalis.caringu.dtos.Feedback.FeedbackCountDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.*;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.exception.Aula.AulaConflitanteException;
import tech.vitalis.caringu.exception.PlanoContratado.AlunoSemPlanoContratadoException;
import tech.vitalis.caringu.exception.SessaoTreino.SessaoTreinoNaoEncontradoException;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.mapper.AulaMapper;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.AulaTreinoExercicioRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;
import tech.vitalis.caringu.strategy.SessaoTreino.StatusSessaoTreinoValidationStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final PlanoContratadoRepository planoContratadoRepository;
    private final AulaMapper aulaMapper;
    private final TreinoExercicioRepository treinoExercicioRepository;
    private final AulaTreinoExercicioRepository aulaTreinoExercicioRepository;
    private final NotificacaoFeedbackTreinoFinalizadoService notificacaoFeedbackTreinoFinalizadoService;

    public AulaService(
            AulaRepository aulaRepository,
            PlanoContratadoRepository planoContratadoRepository,
            AulaMapper aulaMapper,
            TreinoExercicioRepository treinoExercicioRepository,
            AulaTreinoExercicioRepository aulaTreinoExercicioRepository,
            NotificacaoFeedbackTreinoFinalizadoService notificacaoFeedbackTreinoFinalizadoService
            ) {
        this.aulaRepository = aulaRepository;
        this.planoContratadoRepository = planoContratadoRepository;
        this.aulaMapper = aulaMapper;
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.aulaTreinoExercicioRepository = aulaTreinoExercicioRepository;
        this.notificacaoFeedbackTreinoFinalizadoService = notificacaoFeedbackTreinoFinalizadoService;
    }

    public AulasAgendadasResponseDTO listarInfoAulaPorPersonal(Integer idPersonal, Integer idAula) {
        List<AulasAgendadasResponseDTO> aulas = aulaRepository.findAllInfoAulaPorPersonal(idPersonal, idAula);
        return aulas.isEmpty() ? null : aulas.getFirst();
    }

    public List<SessaoAulasAgendadasResponseDTO> listarAulasPorPersonal(Integer idPersonal) {

        List<SessaoAulasAgendadasResponseDTO> aulasPorAluno = aulaRepository.findAllAulasPorPersonal(idPersonal);

        return aulasPorAluno.stream()
                .map(aulaMapper::toSessaoAulasAgendadasResponseDTOComUrlPreAssinada)
                .toList();
    }

    public List<SessaoAulasAgendadasResponseDTO> listarAulasPorAluno(Integer idAluno) {
        List<SessaoAulasAgendadasResponseDTO> aulasPorAluno = aulaRepository.findAllAulasPorAluno(idAluno);

        return aulasPorAluno.stream()
                .map(aulaMapper::toSessaoAulasAgendadasResponseDTOComUrlPreAssinada)
                .toList();
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
        return new AulasRascunhoResponseDTO(aulas);
    }

    public AulaRascunhoResponsePostDTO criarAulasRascunho(
            Integer idAluno,
            AulaRascunhoRequestPostDTO requestDTO
    ) {

        // 1. Buscar plano ativo do aluno
        PlanoContratadoEntity planoAtivo = planoContratadoRepository
                .findFirstByAlunoIdAndStatus(idAluno, StatusEnum.ATIVO)
                .orElseThrow(() -> new AlunoSemPlanoContratadoException("Aluno não possui plano contratado ativo."));

        List<AulaRascunhoItemDTO> aulasDTO = requestDTO.aulas();

        // 1.1 Validar conflitos entre as aulas da requisição
        for (int i = 0; i < aulasDTO.size(); i++) {
            AulaRascunhoItemDTO atual = aulasDTO.get(i);
            for (int j = i + 1; j < aulasDTO.size(); j++) {
                AulaRascunhoItemDTO outra = aulasDTO.get(j);

                boolean sobrepoe = !atual.dataHorarioFim().isBefore(outra.dataHorarioInicio())
                        && !outra.dataHorarioFim().isBefore(atual.dataHorarioInicio());

                if (sobrepoe) {
                    throw new AulaConflitanteException(
                            "Conflito entre aulas da requisição: " +
                                    atual.dataHorarioInicio() + " - " + atual.dataHorarioFim() +
                                    " e " + outra.dataHorarioInicio() + " - " + outra.dataHorarioFim()
                    );
                }
            }
        }

        // 1.2 Validar conflitos com aulas já cadastradas no banco
        for (AulaRascunhoItemDTO dto : aulasDTO) {
            List<Aula> aulasConflitantes = aulaRepository.findAulasNoPeriodo(
                    planoAtivo.getId(),
                    dto.dataHorarioInicio(),
                    dto.dataHorarioFim()
            );

            // Verifica se existe algum conflito real (ignora rascunhos idênticos)
            boolean temConflito = aulasConflitantes.stream().anyMatch(a ->
                    // se não for rascunho, já é conflito
                    a.getStatus() != AulaStatusEnum.RASCUNHO
                            ||
                            // se for rascunho, mas em período diferente, também conflito
                            !(a.getDataHorarioInicio().equals(dto.dataHorarioInicio())
                                    && a.getDataHorarioFim().equals(dto.dataHorarioFim()))
            );

            if (temConflito) {
                throw new AulaConflitanteException(
                        "Já existe uma aula agendada no período: " +
                                dto.dataHorarioInicio().toString().replaceAll("T", " ") +
                                " - " +
                                dto.dataHorarioFim().toString().replaceAll("T", " ")
                );
            }
        }

        // 2. Converter DTOs para entidades
        List<Aula> aulas = aulasDTO.stream()
                .filter(dto -> {
                    List<Aula> existentes = aulaRepository.findAulasNoPeriodo(
                            planoAtivo.getId(),
                            dto.dataHorarioInicio(),
                            dto.dataHorarioFim()
                    );
                    // só permite criar se não existe nenhuma aula RASCUNHO exatamente nesse intervalo
                    return existentes.stream().noneMatch(a -> a.getStatus() == AulaStatusEnum.RASCUNHO);
                })
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

    //visualizar aulas
    public Page<AulasAlunoFeedbackResponseDTO> listarAulasPorAlunoComPlano(Integer idAluno, Pageable pageable, LocalDate data) {
        final Map<Integer, String> diasDaSemana = Map.of(
                7, "Domingo",
                1, "Segunda-feira",
                2, "Terça-feira",
                3, "Quarta-feira",
                4, "Quinta-feira",
                5, "Sexta-feira",
                6, "Sábado"
        );

        LocalDateTime dataInicio = null;
        LocalDateTime dataFim = null;

        if (data != null) {
            dataInicio = data.atStartOfDay();
            dataFim = data.plusDays(1).atStartOfDay();
        }

        Page<AulasAlunoRequestDTO> aulasPage = aulaRepository.listarAulasPorAlunoComPlano(
                idAluno, data, dataInicio, dataFim, pageable
        );

        Map<Integer, Integer> feedbacksMap = new HashMap<>();
        if (!aulasPage.getContent().isEmpty()){
            List<Integer> aulasIds = aulasPage.getContent().stream()
                    .map(AulasAlunoRequestDTO::aulaId)
                    .toList();

            List<FeedbackCountDTO> feedbacksQtd = aulaRepository.buscarQuantidadeFeedbacksPorAulas(aulasIds);
            feedbacksMap = feedbacksQtd.stream()
                    .collect(Collectors.toMap(
                            FeedbackCountDTO::aulaId,
                            FeedbackCountDTO::qtdFeedbacks
                    ));
        }

        final Map<Integer, Integer> feedbacksFinal = feedbacksMap;

        List<AulasAlunoFeedbackResponseDTO> mapperAula = aulasPage.getContent().stream()
                .map(dto -> {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                    int diaSemana = dto.dataHorarioInicio().getDayOfWeek().getValue();

                    return new AulasAlunoFeedbackResponseDTO(
                            dto.aulaId(),
                            dto.dataHorarioInicio().format(dateFormatter),
                            diasDaSemana.getOrDefault(diaSemana, "Dia Desconhecido"),
                            dto.dataHorarioInicio().format(timeFormatter),
                            dto.dataHorarioFim().format(timeFormatter),
                            dto.nomePersonal(),
                            dto.treinoId(),
                            dto.nomeTreino(),
                            feedbacksFinal.getOrDefault(dto.aulaId(), 0)
                    );
                })
                .toList();

        return new PageImpl<>(mapperAula, pageable, aulasPage.getTotalElements());
    }

    public void atualizarStatus(Integer idAula, AulaStatusEnum novoStatus) {

        Aula aula = aulaRepository.findById(idAula)
                .orElseThrow(() -> new SessaoTreinoNaoEncontradoException("Sessão treino com id %d não encontrado.".formatted(idAula)));

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

        if (novoStatus.equals(AulaStatusEnum.REALIZADO)) {
            notificacaoFeedbackTreinoFinalizadoService.notificarTreinoFinalizado(idAula);
        }
    }

    @Transactional
    public void deletarAulasRascunhos(List<Integer> listaIdsAula) {
        if (listaIdsAula == null || listaIdsAula.isEmpty()) {
            return; // nada a deletar
        }

        // Validação: só permite excluir aulas que estejam em rascunho
        List<Aula> aulasParaExcluir = aulaRepository.findAllById(listaIdsAula)
                .stream()
                .filter(aula -> aula.getStatus() == AulaStatusEnum.RASCUNHO)
                .toList();

        if (aulasParaExcluir.isEmpty()) {
            return;
        }

        aulaRepository.deleteAll(aulasParaExcluir);
    }
}
