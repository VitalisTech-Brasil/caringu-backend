package tech.vitalis.caringu.dtos.PlanoContratado;

import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.enums.StatusEnum;

import java.time.LocalDate;

public record PlanoContratadoPagamentoPendenteResponseDTO(
        Integer id,
        Integer planoId,
        Aluno alunoId,
        StatusEnum status,
        LocalDate dataContratacao,
        LocalDate dataFim
) {
}
