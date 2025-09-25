package tech.vitalis.caringu.dtos.Aula.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AulaRascunhoRequestPostDTO(

        @NotNull(message = "A lista de aulas não pode ser nula")
        @NotEmpty(message = "É necessário informar pelo menos uma aula")
        List<AulaRascunhoItemDTO> aulas
) {
}
