package tech.vitalis.caringu.dtos.Aula.Request;

import java.time.LocalDateTime;

public record AulasAlunoRequestDTO(
        Integer aulaId,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        String nomePersonal,
        Integer treinoId
) {
}
