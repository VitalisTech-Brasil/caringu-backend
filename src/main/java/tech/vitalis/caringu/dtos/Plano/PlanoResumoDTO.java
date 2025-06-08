package tech.vitalis.caringu.dtos.Plano;

import tech.vitalis.caringu.enums.PeriodoEnum;

public record PlanoResumoDTO(
        Integer id,
        Integer personalTrainerId,
        String nome,
        PeriodoEnum periodo,
        Integer quantidadeAulas,
        Double valorAulas
) {}
