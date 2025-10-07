package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;

import java.math.BigDecimal;

public record VisualizarAulasItemResponseDTO(
        Integer idAula,
        Integer idExecucaoExercicio,
        String nomeExercicio,
        BigDecimal carga,
        String repeticoesSeries,
        GrupoMuscularEnum grupoMuscular,
        String observacoes,
        String urlVideoExecucao,
        boolean aulaRealizada
) {
}
