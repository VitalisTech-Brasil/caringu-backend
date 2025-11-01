package tech.vitalis.caringu.service;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Feedback.FeedbackItemDTO;
import tech.vitalis.caringu.dtos.Feedback.FeedbackPorAulaDTO;
import tech.vitalis.caringu.dtos.Feedback.FeedbackPorDataDTO;
import tech.vitalis.caringu.repository.FeedbackRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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
}
