package tech.vitalis.caringu.dtos.TreinoExercicio;

public record ExerciciosPorTreinoResponseDTO(
        Integer alunoTreinoExercicioId,
        Integer exercicioId,
        String nomeExercicio,
        String nomeTreino
) {
}
