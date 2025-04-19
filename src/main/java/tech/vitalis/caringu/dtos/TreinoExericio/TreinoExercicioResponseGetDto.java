package tech.vitalis.caringu.dtos.TreinoExericio;

import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.entity.Exercicio;

import java.time.LocalDateTime;

public record TreinoExercicioResponseGetDto(
        Integer id,
        TreinoResponseGetDTO treinos,
        ExercicioResponseGetDTO exercicio,
        Double carga,
        Integer repeticoes,
        Integer series,
        Integer descanso,
        LocalDateTime dataHoraCriacao,
        LocalDateTime dataHoraModificacao,
        Boolean favorito,
        String grauDificuldade
){}
