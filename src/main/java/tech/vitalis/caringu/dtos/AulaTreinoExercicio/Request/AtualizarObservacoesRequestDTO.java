package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarObservacoesRequestDTO(
        @NotBlank
        String observacoes
) {
}
