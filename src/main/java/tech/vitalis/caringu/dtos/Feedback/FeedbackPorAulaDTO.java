package tech.vitalis.caringu.dtos.Feedback;

import tech.vitalis.caringu.enums.Feedback.TipoAutorEnum;

import java.time.LocalDateTime;

public record FeedbackPorAulaDTO (
        LocalDateTime dataAula,
        TipoAutorEnum autorTipo,
        Integer autorId,
        String descricao,
        LocalDateTime dataCriacao
){
}
