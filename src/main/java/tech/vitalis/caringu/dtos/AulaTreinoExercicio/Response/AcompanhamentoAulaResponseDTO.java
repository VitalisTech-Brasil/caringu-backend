package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public record AcompanhamentoAulaResponseDTO(
        Integer idAluno,
        Integer idAula,
        AulaStatusEnum aulaStatus,
        LocalDateTime dataInicioAula,
        LocalDateTime dataFimAula,
        Integer idTreino,
        String nomeTreino,
        List<ExercicioAcompanhamentoAulaDTO> exercicios
) {
}
