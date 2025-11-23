package tech.vitalis.caringu.dtos.AulaTreinoExercicio;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AcompanhamentoAulaCruDTO(
        Integer idAluno,
        Integer idAula,
        AulaStatusEnum aulaStatus,
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
        GrupoMuscularEnum grupoMuscular,
        Boolean finalizado
) {
}
