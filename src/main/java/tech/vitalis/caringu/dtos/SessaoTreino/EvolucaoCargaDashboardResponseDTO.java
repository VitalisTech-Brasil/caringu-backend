package tech.vitalis.caringu.dtos.SessaoTreino;

import java.time.LocalDateTime;

public record EvolucaoCargaDashboardResponseDTO(
        Integer idAluno,
        String nomeAluno,
        Double pesoAluno,
        Double alturaAluno,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        Double cargaUtilizada,
        String nomeExercicio
) {
}
