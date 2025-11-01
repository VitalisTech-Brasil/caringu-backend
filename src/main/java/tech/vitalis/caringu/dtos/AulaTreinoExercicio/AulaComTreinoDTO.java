package tech.vitalis.caringu.dtos.AulaTreinoExercicio;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO que representa uma aula com informações de treino, personal e lista de exercícios.
 */
public record AulaComTreinoDTO(

        @Schema(description = "Identificador único do aluno", example = "202")
        Integer idAluno,

        @Schema(description = "Nome completo do aluno", example = "Carla Mendes")
        String nomeAluno,

        @Schema(description = "Identificador único da aula", example = "1")
        Integer idAula,

        @Schema(description = "Data em que a aula ocorrerá (formato dd/MM/yyyy)", example = "31/10/2025")
        String dataAula,

        @Schema(description = "Dia da semana referente à data da aula", example = "Sexta-feira")
        String diaSemana,

        @Schema(description = "Horário formatado de início e fim da aula", example = "09:00 - 10:00")
        String horarioInicioFim,

        @Schema(description = "Identificador do treino vinculado à aula", example = "101")
        Integer idTreino,

        @Schema(description = "Nome do treino associado à aula", example = "Treino de Força")
        String nomeTreino,

        @Schema(description = "Lista de exercícios pertencentes ao treino")
        List<ExercicioResumoDTO> exercicios,

        @Schema(description = "Nome completo do personal responsável pela aula", example = "Mônica Luiz Borges Moreno")
        String nomePersonal,

        @Schema(description = "URL da foto de perfil do personal", example = "https://amazonaws.com/foto_perfil_monica.jpg")
        String urlFotoPerfil
) {}

