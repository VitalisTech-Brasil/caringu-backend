package tech.vitalis.caringu.dtos.PersonalTrainer;

import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PersonalTrainerResponseGetDTO(
        Integer id,
        String nome,
        String email,
        String celular,
        String urlFotoPerfil,
        LocalDate dataNascimento,
        GeneroEnum genero,

        String cref,
        List<String> especialidade,
        Integer experiencia,
        LocalDateTime dataCadastro
) {
}
