package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.time.LocalDateTime;

public record AulaCriadaDTO(
        Integer idAula,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim
) {}