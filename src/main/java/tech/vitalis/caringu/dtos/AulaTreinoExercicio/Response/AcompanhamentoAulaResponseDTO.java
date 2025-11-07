package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.time.LocalDateTime;
import java.util.List;

public record AcompanhamentoAulaResponseDTO(
        Integer idAula,
        LocalDateTime dataInicioAula,
        LocalDateTime dataFimAula,
        Integer idTreino,
        String nomeTreino,
        List<ExercicioAcompanhamentoAulaDTO> exercicios
) {
}
