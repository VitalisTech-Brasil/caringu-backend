package tech.vitalis.caringu.dtos.Exercicio;

public record ExercicioResponseGetDTO(
         Integer id,
         String nome,
         String grupoMuscular,
         String urlVideo,
         String observacoes,
         Boolean favorito,
         String origem

){}
