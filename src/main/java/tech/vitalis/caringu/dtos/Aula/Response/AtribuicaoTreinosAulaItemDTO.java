package tech.vitalis.caringu.dtos.Aula.Response;

import java.time.LocalDateTime;

public record AtribuicaoTreinosAulaItemDTO(
        Integer idAluno,
        Integer idAula,
        Integer idPlanoContratado,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim
) {
}
