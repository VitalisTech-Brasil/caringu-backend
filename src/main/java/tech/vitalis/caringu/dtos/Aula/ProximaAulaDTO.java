package tech.vitalis.caringu.dtos.Aula;

import java.time.LocalDateTime;

public record ProximaAulaDTO(
        Integer aulaTreinoExercicioId,
        Integer aulaId,
        LocalDateTime dataHorarioInicio,
        LocalDateTime dataHorarioFim,
        Integer treinoId,
        String nomeTreino,
        Integer exercicioId,
        String nomeExercicio,
        Integer personalId,
        String nomePersonal,
        String urlFotoPerfil
) {}

