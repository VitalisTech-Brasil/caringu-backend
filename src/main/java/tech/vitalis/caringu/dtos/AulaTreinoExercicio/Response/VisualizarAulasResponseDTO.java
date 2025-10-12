package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.util.List;

public record VisualizarAulasResponseDTO(
        Integer idTreino,
        String nomeTreino,
        List<VisualizarAulasItemResponseDTO> exercicios
) {
}
