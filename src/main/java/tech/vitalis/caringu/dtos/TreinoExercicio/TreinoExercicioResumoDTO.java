package tech.vitalis.caringu.dtos.TreinoExercicio;

import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

public record TreinoExercicioResumoDTO(
        Integer treinoExercicioId,
        Integer treinoId,
        String nomeTreino,
        GrauDificuldadeEnum grauDificuldade,
        Boolean favorito,
        OrigemTreinoExercicioEnum origemTreinoExercicio,
        int quantidadeExercicios
) {
}
