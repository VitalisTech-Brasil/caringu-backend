package tech.vitalis.caringu.dtos.Treino;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TreinoRequestPostDTO (

        @NotBlank(message = "Nome treino obrigatório")
        String nome,

        @NotBlank(message = "Descrição treino obrigatório")
        String descricao,

        @NotNull(message = "Descrição treino obrigatório")
        Integer personalId
){

}
