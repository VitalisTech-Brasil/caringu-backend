package tech.vitalis.caringu.dtos.PersonalTrainer;

import io.swagger.v3.oas.annotations.media.Schema;
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

        @Schema(description = "Nomes das especialidades do personal trainer")
        List<String> especialidades,

        Integer experiencia,
        LocalDateTime dataCadastro
) {
}
