package tech.vitalis.caringu.dtos.TreinoFinalizado;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.dtos.AlunosTreino.AlunoTreinoResponseGetDTO;
import tech.vitalis.caringu.entity.AlunoTreino;

import java.time.LocalDateTime;

public record TreinoFinalizadoResponseGetDTO(
        Integer id,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        AlunoTreinoResponseGetDTO alunoTreino
) {
}
