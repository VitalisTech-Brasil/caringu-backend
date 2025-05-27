package tech.vitalis.caringu.dtos.TreinoExercicio;

import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

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
        OrigemTreinoExercicioEnum origemTreinoExercicio,
        Boolean favorito,
        GrauDificuldadeEnum grauDificuldade
){}
