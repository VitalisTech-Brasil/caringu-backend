package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.math.BigDecimal;

public record ExercicioAcompanhamentoAulaDTO(
        Integer idExecucaoExercicio,
        String nomeExercicio,
        BigDecimal cargaKg,
        String repeticoesSeries,
        Integer descansoSegundos,
        String observacoes,
        String urlExemploExecucao,
        Boolean finalizado
) {
}
