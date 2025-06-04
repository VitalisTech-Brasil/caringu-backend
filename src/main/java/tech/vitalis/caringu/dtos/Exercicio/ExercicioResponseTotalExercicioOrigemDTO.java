package tech.vitalis.caringu.dtos.Exercicio;

import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;

public record ExercicioResponseTotalExercicioOrigemDTO(
        Long totalExercicio,
        OrigemEnum origem
) {
}
