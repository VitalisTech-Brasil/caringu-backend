package tech.vitalis.caringu.dtos.TreinoFinalizado;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.entity.AlunoTreino;

import java.time.LocalDateTime;

public record TreinoFinalizadoRequestPostDTO(
         @FutureOrPresent
         LocalDateTime dataHorarioInicio,
         @FutureOrPresent
         LocalDateTime dataHorarioFim,
         @NotNull
         Integer alunoTreino
) {
}
