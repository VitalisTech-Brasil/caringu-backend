package tech.vitalis.caringu.dtos.TreinoExericio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.enums.NivelTreinoExercicioEnum;

import java.time.LocalDateTime;

public record TreinoExercicioRequestPostDto(
        Integer treinos,
        Integer exercicio,
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
        Boolean favorito,
        @NotNull(message = "Grau de Dificuldade é obrigatório ")
        String grauDificuldade
) {

}
