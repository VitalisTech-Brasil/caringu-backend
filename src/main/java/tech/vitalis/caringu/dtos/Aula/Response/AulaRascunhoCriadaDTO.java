package tech.vitalis.caringu.dtos.Aula.Response;

import java.time.LocalDateTime;

public record AulaRascunhoCriadaDTO(
        Integer aulaId,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        String status
) {
}
