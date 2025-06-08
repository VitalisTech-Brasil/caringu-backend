package tech.vitalis.caringu.dtos.TreinoFinalizado;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.entity.AlunoTreino;

import java.time.LocalDateTime;

public record TreinoFinalizadpRequestUpdateDTO(
        @FutureOrPresent
        LocalDateTime dataHorarioInicio,
        @FutureOrPresent
        LocalDateTime dataHorarioFim,
        @NotNull
        Integer alunoTreino
) {
}
