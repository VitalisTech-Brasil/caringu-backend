package tech.vitalis.caringu.dtos.TreinoExercicio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TreinoExercicioAssociacaoRequestDTO(
        @NotNull(message = "O campo idTreino não pode estar nulo.")
        Integer idTreino,
        @NotEmpty
        List<@Valid TreinoExercicioRequestPostDto> exercicios
) {
}
