package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;

import java.math.BigDecimal;

public record AulaTreinoExercicioRemarcarAulaResponseDTO(
        Integer idAulaTreinoExercicio,
        Integer idTreinoExercicio,
        Integer idExercicio,
        String nomeExercicio,
        GrupoMuscularEnum grupoMuscular,
        Integer ordem,
        BigDecimal carga,
        Integer repeticoes,
        Integer series,
        Integer descanso,
        String observacoesPersonalizadas
) {
}
