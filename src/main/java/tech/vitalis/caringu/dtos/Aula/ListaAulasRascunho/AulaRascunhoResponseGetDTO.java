package tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDateTime;

public record AulaRascunhoResponseGetDTO(
        Integer aulaId,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        AulaStatusEnum status,
        Integer idPlanoContratado
) {
}
