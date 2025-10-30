package tech.vitalis.caringu.dtos.Feedback;

import java.util.List;

public record FeedbackPorDataDTO (
        String data,
        List<FeedbackItemDTO> feedbacks
){
}
