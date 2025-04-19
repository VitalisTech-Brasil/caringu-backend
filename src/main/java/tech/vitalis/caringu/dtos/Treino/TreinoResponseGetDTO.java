package tech.vitalis.caringu.dtos.Treino;

import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;

public record TreinoResponseGetDTO(
         Integer id,
         String nome,
         String descricao,
         PersonalTrainerResponseGetDTO personal
) {}
