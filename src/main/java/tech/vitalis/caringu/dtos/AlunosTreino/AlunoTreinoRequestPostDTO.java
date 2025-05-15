package tech.vitalis.caringu.dtos.AlunosTreino;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record AlunoTreinoRequestPostDTO(

        Integer alunosId,
        Integer treinosExerciciosId,
        @FutureOrPresent
        @NotNull
        LocalDateTime dataHorarioInicio,
        @Future
        @NotNull
        LocalDateTime dataHorarioFim,
        @NotEmpty(message = "É necessário informar ao menos uma especialidade")
        @Schema(description = "Lista de especialidades")
        List<@NotBlank(message = "Especialidade não pode ser vazia") String> diasSemana,
        @PositiveOrZero
        Integer periodoAvaliacao,
        @FutureOrPresent
        LocalDateTime dataVencimento

) {
}
