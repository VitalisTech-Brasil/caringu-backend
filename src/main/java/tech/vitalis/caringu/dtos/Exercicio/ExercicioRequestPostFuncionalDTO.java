package tech.vitalis.caringu.dtos.Exercicio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;

public record ExercicioRequestPostFuncionalDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O grupo muscular é obrigatório")
        GrupoMuscularEnum grupoMuscular,
        String urlVideo,
        String observacoes
) {}
