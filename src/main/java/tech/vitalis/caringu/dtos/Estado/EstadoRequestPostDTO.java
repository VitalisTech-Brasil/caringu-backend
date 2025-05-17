package tech.vitalis.caringu.dtos.Estado;

import jakarta.validation.constraints.NotNull;

public record EstadoRequestPostDTO(
        @NotNull(message = "O campo 'nome' é obrigatório.")
        String nome
) {
}
