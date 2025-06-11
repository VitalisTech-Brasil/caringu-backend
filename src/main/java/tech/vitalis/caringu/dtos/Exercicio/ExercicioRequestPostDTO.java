package tech.vitalis.caringu.dtos.Exercicio;

import jakarta.validation.constraints.NotBlank;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;

public record ExercicioRequestPostDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O grupo muscular é obrigatório")
        GrupoMuscularEnum grupoMuscular,
        String urlVideo,
        String observacoes,
        Boolean favorito,
        @NotBlank
        OrigemEnum origem
) {}
