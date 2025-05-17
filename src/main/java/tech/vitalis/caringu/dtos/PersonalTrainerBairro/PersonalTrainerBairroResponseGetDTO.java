package tech.vitalis.caringu.dtos.PersonalTrainerBairro;

public record PersonalTrainerBairroResponseGetDTO(
        Integer id,
        Integer personalTrainerId,
        String personalTrainerNome,
        Integer bairroId,
        String bairroNome
) {
}
