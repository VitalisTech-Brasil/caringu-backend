package tech.vitalis.caringu.dtos.Feedback;

import tech.vitalis.caringu.enums.Feedback.TipoAutorEnum;

import java.time.LocalDateTime;

public record CriacaoFeedbackDto (
        Integer autorId,
        Integer aulaId,
        TipoAutorEnum autorTipo,
        String descricao,
        LocalDateTime dataCriacao
){}
