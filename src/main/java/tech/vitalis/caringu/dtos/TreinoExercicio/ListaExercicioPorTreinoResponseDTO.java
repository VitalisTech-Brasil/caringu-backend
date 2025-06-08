package tech.vitalis.caringu.dtos.TreinoExercicio;

public record ListaExercicioPorTreinoResponseDTO(
        Integer treinoExercicioId,
        Integer exercicioId,
        String nomeExercicio,
        String nomeTreino
) {
}
