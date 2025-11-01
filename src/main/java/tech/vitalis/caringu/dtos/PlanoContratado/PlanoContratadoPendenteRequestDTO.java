package tech.vitalis.caringu.dtos.PlanoContratado;

import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;

public record PlanoContratadoPendenteRequestDTO(
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
