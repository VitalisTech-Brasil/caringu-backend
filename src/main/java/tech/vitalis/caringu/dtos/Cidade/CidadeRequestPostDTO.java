package tech.vitalis.caringu.dtos.Cidade;

import jakarta.validation.constraints.NotNull;

public record CidadeRequestPostDTO(
        @NotNull(message = "O campo 'nome' é obrigatório.")
        String nome,

        @NotNull(message = "O campo 'estadoId' é obrigatório")
        Integer estadoId
) {
}
