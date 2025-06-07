package tech.vitalis.caringu.dtos.TreinoExercicio;

public record ListaExercicioPorTreinoResponseDTO(
        Integer treinoExercicioId,
        Integer treinoId,
        Integer exercicioId,
        String nomeExercicio
) {
}
