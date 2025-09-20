package tech.vitalis.caringu.dtos.TreinoExercicio;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TreinoExercicioEditResponseGetDTO(

        String nomeTreino,
        String descricaoTreino,
        Integer personalId,

        Integer idTreinoExercicio,
        Integer treinoId,
        Integer exercicioId,
        BigDecimal carga,
        Integer repeticoes,
        Integer series,
        Integer descanso,
        LocalDateTime dataHoraModificacao,
        OrigemTreinoExercicioEnum origemTreinoExercicio,
        Boolean favoritoTreino,
        GrauDificuldadeEnum grauDificuldade,

        String nomeExercicio,
        GrupoMuscularEnum grupoMuscular,
        String urlVideo,
        String observacoes,
        Boolean favoritoExercicio,
        OrigemEnum origemExercicio
) {
}
