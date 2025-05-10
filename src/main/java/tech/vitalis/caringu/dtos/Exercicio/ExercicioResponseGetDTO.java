package tech.vitalis.caringu.dtos.Exercicio;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;

public record ExercicioResponseGetDTO(
         Integer id,
         String nome,
         GrupoMuscularEnum grupoMuscular,
         String urlVideo,
         String observacoes,
         Boolean favorito,
         OrigemEnum origem

){}
