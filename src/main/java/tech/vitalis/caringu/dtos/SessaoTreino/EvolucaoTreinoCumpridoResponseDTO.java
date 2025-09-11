package tech.vitalis.caringu.dtos.SessaoTreino;

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
