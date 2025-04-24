package tech.vitalis.caringu.dtos.Exercicio;

import jakarta.validation.constraints.NotBlank;

public record ExercicioRequestPostDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O grupo muscular é obrigatório")
        String grupoMuscular,
        @NotBlank(message = "A url é obrigatório")
        String urlVideo,
        String observacoes,
        Boolean favorito,
        @NotBlank
        String origem
) {}
