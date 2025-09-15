package tech.vitalis.caringu.dtos.AlunosTreinoExercicio;

public record ExerciciosPorTreinoResponseDTO(
        Integer alunoTreinoExercicioId,
        Integer exercicioId,
        String nomeExercicio,
        String nomeTreino
) {
}
