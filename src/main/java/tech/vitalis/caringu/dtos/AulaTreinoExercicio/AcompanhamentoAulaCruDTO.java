package tech.vitalis.caringu.dtos.AulaTreinoExercicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AcompanhamentoAulaCruDTO(
        Integer idAula,
        LocalDateTime dataInicioAula,
        LocalDateTime dataFimAula,
        Integer idTreino,
        String nomeTreino,

        Integer idExecucaoExercicio,
        String nomeExercicio,
        BigDecimal cargaExecutada,
        Integer repeticoesExecutadas,
        Integer seriesExecutadas,
        Integer descansoExecutado,
        String observacoesPersonalizadas,
        String urlVideo,
        Boolean finalizado
) {
}
