package tech.vitalis.caringu.dtos.SessaoTreino;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EvolucaoCargaDashboardResponseDTO(
        Integer idAluno,
        String nomeAluno,
        Double pesoAluno,
        Double alturaAluno,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        BigDecimal cargaUtilizada,
        String nomeExercicio
) {
}
