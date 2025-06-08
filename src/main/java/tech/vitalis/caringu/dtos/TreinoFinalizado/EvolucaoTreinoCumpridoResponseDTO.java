package tech.vitalis.caringu.dtos.TreinoFinalizado;

import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;

public record EvolucaoTreinoCumpridoResponseDTO(
        Integer idAluno,
        String nomeAluno,
        Integer idExercicio,
        String nomeExercicio,
        Integer ano,
        Integer mes,
        Long treinosRealizados,
        FrequenciaTreinoEnum frequenciaEsperadaPorSemana
) {
}
