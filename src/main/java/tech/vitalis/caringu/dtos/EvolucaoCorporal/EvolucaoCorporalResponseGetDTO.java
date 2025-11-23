package tech.vitalis.caringu.dtos.EvolucaoCorporal;

import tech.vitalis.caringu.enums.EvolucaoCorporal.TipoEvolucaoEnum;

import java.time.LocalDateTime;

public record EvolucaoCorporalResponseGetDTO(
        Integer id,
        TipoEvolucaoEnum tipo,
        String urlFotoShape,
        LocalDateTime dataEnvio,
        Integer periodoAvaliacao
) {
}
