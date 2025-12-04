package tech.vitalis.caringu.dtos.AulaTreinoExercicio;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;

import java.math.BigDecimal;

public record TreinoDetalhadoRepositoryDTO(
        Integer idTreino,
        String nomeTreino,
        Integer idTreinoExercicio,
        Integer idAula,
        Integer idExecucaoExercicio,
        String nomeExercicio,
        Integer descanso,
        String repeticoesSeries,
        String carga,
        GrupoMuscularEnum grupoMuscular,
        Integer idAulaTreinoExercicio,
        String observacoes,
        String urlVideoExecucao,
        Boolean aulaRealizada
) {
}
