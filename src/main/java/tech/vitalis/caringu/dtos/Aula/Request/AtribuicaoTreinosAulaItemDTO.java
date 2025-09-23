package tech.vitalis.caringu.dtos.Aula.Request;

import java.time.LocalDateTime;

public record AtribuicaoTreinosAulaItemDTO(
        Integer idAluno,
        Integer idPlanoContratado,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim
) {
}
