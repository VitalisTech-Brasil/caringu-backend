package tech.vitalis.caringu.dtos.AulaTreinoExercicio;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que representa um exercício de um treino.
 */
public record ExercicioResumoDTO(

        @Schema(description = "Identificador único do exercício", example = "1001")
        Integer idExercicio,

        @Schema(description = "Nome do exercício", example = "Agachamento")
        String nomeExercicio
) {
}