package tech.vitalis.caringu.dtos.TreinoExercicio;

import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

public record TreinoExercicioResumoModeloCruQuerySqlDTO(
        String nomeExercicio,
        Integer exercicioId,
        Integer treinoId,
        String nomeTreino,
        GrauDificuldadeEnum grauDificuldade,
        Boolean favorito,
        OrigemTreinoExercicioEnum origemTreinoExercicio
) {
}
