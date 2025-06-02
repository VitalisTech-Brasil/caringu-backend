package tech.vitalis.caringu.dtos.TreinoExercicio;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

import java.time.LocalDateTime;

public record TreinoExercicioEditResponseGetDTO(

        String nomeTreino,
        String descricaoTreino,
        Integer personalId,

        Integer idTreinoExercicio,
        Integer treinoId,
        Integer exercicioId,
        Double carga,
        Integer repeticoes,
        Integer series,
        Integer descanso,
        LocalDateTime dataHoraCriacao,
        LocalDateTime dataHoraModificacao,
        OrigemTreinoExercicioEnum origemTreinoExercicio,
        Boolean favoritoTreinoExercicio,
        GrauDificuldadeEnum grauDificuldade,

        String nomeExercicio,
        GrupoMuscularEnum grupoMuscular,
        String urlVideo,
        String observacoes,
        Boolean favoritoExercicio,
        OrigemEnum origemExercicio
) {
}
