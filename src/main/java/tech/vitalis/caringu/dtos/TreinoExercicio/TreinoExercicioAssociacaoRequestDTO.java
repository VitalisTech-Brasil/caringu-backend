package tech.vitalis.caringu.dtos.TreinoExercicio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TreinoExercicioAssociacaoRequestDTO(
        @NotNull
        Integer treinoId,
        @NotEmpty
        List<@Valid TreinoExercicioRequestPostDto> exercicios
) {
}
