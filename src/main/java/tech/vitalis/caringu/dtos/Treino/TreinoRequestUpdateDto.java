package tech.vitalis.caringu.dtos.Treino;

import jakarta.validation.constraints.NotBlank;

public record TreinoRequestUpdateDto(
        @NotBlank(message = "Nome treino obrigatório")
        String nome,

        @NotBlank(message = "Descrição treino obrigatório")
        String descricao
) {
}
