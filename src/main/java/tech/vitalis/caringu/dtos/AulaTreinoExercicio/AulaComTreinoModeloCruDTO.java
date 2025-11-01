package tech.vitalis.caringu.dtos.AulaTreinoExercicio;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AulaComTreinoModeloCruDTO(

        @Schema(description = "Identificador único do aluno", example = "202")
        Integer idAluno,

        @Schema(description = "Nome completo do aluno", example = "Carla Mendes")
        String nomeAluno,

        @Schema(description = "Identificador do relacionamento AulaTreinoExercicio", example = "101")
        Integer aulaTreinoExercicioId,

        @Schema(description = "Identificador único da aula", example = "1")
        Integer idAula,

        @Schema(description = "Data e hora de início da aula", example = "2025-10-31T09:00:00")
        LocalDateTime dataHorarioInicio,

        @Schema(description = "Data e hora de término da aula", example = "2025-10-31T10:00:00")
        LocalDateTime dataHorarioFim,

        @Schema(description = "Identificador do treino associado à aula", example = "101")
        Integer treinoId,

        @Schema(description = "Nome do treino associado à aula", example = "Treino de Força")
        String nomeTreino,

        @Schema(description = "Identificador do exercício dentro do treino", example = "1001")
        Integer exercicioId,

        @Schema(description = "Nome do exercício dentro do treino", example = "Agachamento")
        String nomeExercicio,

        @Schema(description = "Identificador do personal trainer responsável", example = "7")
        Integer personalId,

        @Schema(description = "Nome completo do personal trainer", example = "Mônica Luiz Borges Moreno")
        String nomePersonal,

        @Schema(description = "URL da foto de perfil do personal trainer", example = "https://amazonaws.com/foto_perfil_monica.jpg")
        String urlFotoPerfil
) {}