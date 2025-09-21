package tech.vitalis.caringu.dtos.Aula.Request;

import java.time.LocalDateTime;

public record AulaRascunhoItemDTO(
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim
) {}
