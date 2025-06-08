package tech.vitalis.caringu.dtos.EvolucaoCorporal;

import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.enums.EvolucaoCorporal.TipoEvolucaoEnum;

import java.time.LocalDateTime;

public record EvolucaoCorporalRequestPostDTO(
        @NotNull(message = "O campo 'tipo' é obrigatório.")
        TipoEvolucaoEnum tipo,

        String urlFotoShape,
        LocalDateTime dataEnvio,

        @NotNull(message = "O campo 'periodoAvaliacao' é obrigatório.")
        Integer periodoAvaliacao,

        @NotNull(message = "O campo 'alunoId' é obrigatório.")
        Integer alunoId
) {
}
