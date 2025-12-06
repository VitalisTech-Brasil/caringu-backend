package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.math.BigDecimal;

public record ExercicioAcompanhamentoAulaDTO(
        Integer idExecucaoExercicio,
        String nomeExercicio,
        BigDecimal cargaKg,
        String repeticoesSeries,
        Integer descansoSegundos,
        Integer idAulaTreinoExercicio,
        String observacoes,
        String urlExemploExecucao,
        String grupoMuscular,
        Boolean finalizado
) {
}
