package tech.vitalis.caringu.service;

import com.google.api.client.util.DateTime;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aula.Request.AulasAlunoRequestDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulasAlunoFeedbackResponseDTO;
import tech.vitalis.caringu.dtos.Feedback.*;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.Feedback;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.FeedbackRepository;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AulaRepository aulaRepository;
    private final PessoaRepository pessoaRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, AulaRepository aulaRepository, PessoaRepository pessoaRepository) {
        this.feedbackRepository = feedbackRepository;
        this.aulaRepository = aulaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<FeedbackPorDataDTO> buscarFeedbacksPorAula(Integer aulaId){
        List<FeedbackPorAulaDTO> feedbacks = feedbackRepository.buscarFeedbacksPorAula(aulaId);

        Map<LocalDate, List<FeedbackItemDTO>> feedbacksPorData = new LinkedHashMap<>();

        for (FeedbackPorAulaDTO feedback : feedbacks){
            LocalDate data = feedback.dataAula().toLocalDate();

            FeedbackItemDTO item = new FeedbackItemDTO(
                feedback.autorTipo().name(),
                feedback.autorId(),
                feedback.descricao(),
                feedback.dataCriacao().format(DATETIME_FORMATTER)
            );

            feedbacksPorData.computeIfAbsent(data, f -> new ArrayList<>()).add(item);
        }

        List<FeedbackPorDataDTO> resultado = new ArrayList<>();
        for(Map.Entry<LocalDate, List<FeedbackItemDTO>> entry : feedbacksPorData.entrySet()){
            resultado.add(new FeedbackPorDataDTO(
                    entry.getKey().toString(),
                    entry.getValue()
            ));
        }

        return resultado;
    }

    public RespostaFeedbackDto criarFeedback(CriacaoFeedbackDto dto) {

        Aula aula = aulaRepository.findById(dto.aulaId())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        Pessoa pessoa = pessoaRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Feedback feedback = new Feedback(
                null,
                aula,
                pessoa,
                dto.descricao(),
                dto.dataCriacao(),
                dto.autorTipo(),
                null
        );

        Feedback salvo = feedbackRepository.save(feedback);

        return new RespostaFeedbackDto(
                salvo.getAula().getId(),
                salvo.getPessoa().getId(),
                salvo.getTipoAutor().name(),
                salvo.getDescricao(),
                salvo.getDataCriacao().toString()
        );
    }

    public Page<AulasAlunoFeedbackResponseDTO> listarFeedbacksPorAlunoComPlano(Integer idAluno, Pageable pageable, LocalDate data) {
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

        Page<AulasAlunoRequestDTO> aulasPage = aulaRepository.listarAulasComFeedbacksPorAluno(
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
}
