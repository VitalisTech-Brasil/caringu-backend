package tech.vitalis.caringu.service;

import com.google.api.client.util.DateTime;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}
