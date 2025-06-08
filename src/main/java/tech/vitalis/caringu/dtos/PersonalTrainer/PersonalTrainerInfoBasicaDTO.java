package tech.vitalis.caringu.dtos.PersonalTrainer;

import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

public record PersonalTrainerInfoBasicaDTO(
        Integer id,
        String nomePersonal,
        String email,
        String celular,
        String urlFotoPerfil,
        GeneroEnum genero,
        Integer experiencia,
        String bairro,
        String cidade
) {
}
