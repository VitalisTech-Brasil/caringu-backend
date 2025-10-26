package tech.vitalis.caringu.infrastructure.web.planoContratado;

import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;

import java.time.LocalDate;

public record GetPlanoContratadoPagamentoPendenteResponse(
        Integer id,
        Integer planoId,
        AlunoResponseGetDTO alunoId,
        StatusEnum status,
        LocalDate dataContratacao,
        LocalDate dataFim
) {
}
