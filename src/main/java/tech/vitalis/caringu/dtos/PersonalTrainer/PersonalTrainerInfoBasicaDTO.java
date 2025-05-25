package tech.vitalis.caringu.dtos.PersonalTrainer;

public record PersonalTrainerInfoBasicaDTO(
        Integer id,
        String nomePersonal,
        String email,
        String celular,
        Integer experiencia,
        String bairro,
        String cidade
) {
}
