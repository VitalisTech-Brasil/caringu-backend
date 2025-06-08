package tech.vitalis.caringu.dtos.TreinoExercicio;

import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

public record TreinoExercicioResumoDTO(
        Integer treinoId,
        String nomeTreino,
        GrauDificuldadeEnum grauDificuldade,
        OrigemTreinoExercicioEnum origemTreinoExercicio,
        Boolean favorito,
        int quantidadeExercicios
) {
}
