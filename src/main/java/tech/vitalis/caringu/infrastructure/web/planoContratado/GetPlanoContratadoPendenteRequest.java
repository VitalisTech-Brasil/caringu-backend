package tech.vitalis.caringu.infrastructure.web.planoContratado;

import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.enums.PeriodoEnum;

public record GetPlanoContratadoPendenteRequest(
        Integer id,
        String nomeAluno,
        String celular,
        String nomePlano,
        PeriodoEnum periodo,
        Integer quantidadeAulas,
        Double valorAulas,
        StatusEnum status
) {
}
