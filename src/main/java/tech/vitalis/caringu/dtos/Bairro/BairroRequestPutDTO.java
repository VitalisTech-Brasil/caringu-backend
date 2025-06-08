package tech.vitalis.caringu.dtos.Bairro;

import jakarta.validation.constraints.NotNull;

public record BairroRequestPutDTO(
        @NotNull(message = "O campo 'nome' é obrigatório.")
        String nome,

        @NotNull(message = "O campo 'cidadeId' é obrigatório")
        Integer cidadeId
) {
}
