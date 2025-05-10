package tech.vitalis.caringu.dtos.TreinoExericio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

import java.time.LocalDateTime;

public record TreinoExercicioRequestUpdateDto(
        @Positive
        @NotNull(message = "Carga é obrigatória e não pode ser zero")
        Double carga,
        @Positive
        @NotNull(message = "Repetições é obrigatório e não pode ser zero")
        Integer repeticoes,
        @Positive
        @NotNull(message = "Series é obrigatória e não pode ser zero")
        Integer series,
        @Positive
        @NotNull(message = "Descanso é obrigatóri0 e não pode ser zero")
        Integer descanso,
        @NotNull(message = "Hora de Criaçãp é obrigatória")
        LocalDateTime dataHoraCriacao,
        LocalDateTime dataHoraModificacao,
        @NotBlank(message = "O Origem do Treino Exercício é obrigatório.")
        OrigemTreinoExercicioEnum origemTreinoExercicio,
        Boolean favorito,
        @NotNull(message = "Grau de Dificuldade é obrigatório ")
        GrauDificuldadeEnum grauDificuldade
) {
}
