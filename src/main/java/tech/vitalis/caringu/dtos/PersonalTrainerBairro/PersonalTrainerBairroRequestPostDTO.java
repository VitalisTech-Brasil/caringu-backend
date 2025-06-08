package tech.vitalis.caringu.dtos.PersonalTrainerBairro;

import jakarta.validation.constraints.NotNull;

public record PersonalTrainerBairroRequestPostDTO(
        @NotNull(message = "O ID do personal trainer é obrigatório")
        Integer personalTrainerId,

        @NotNull(message = "O ID do bairro é obrigatório")
        Integer bairroId
) {
}
