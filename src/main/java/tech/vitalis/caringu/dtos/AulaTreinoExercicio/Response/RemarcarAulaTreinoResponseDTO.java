package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.time.LocalDateTime;
import java.util.List;

public record RemarcarAulaTreinoResponseDTO(
        Integer idAula,
        Integer idTreinoNovo,
        LocalDateTime novoHorarioInicio,
        LocalDateTime novoHorarioFim,
        List<AulaTreinoExercicioRemarcarAulaResponseDTO> exercicios
) {
}
