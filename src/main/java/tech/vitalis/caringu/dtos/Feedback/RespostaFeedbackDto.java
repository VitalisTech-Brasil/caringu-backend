package tech.vitalis.caringu.dtos.Feedback;

public record RespostaFeedbackDto (
        Integer aulaId,
        Integer autorId,
        String autorTipo,
        String descricao,
        String dataCriacao
){
}
