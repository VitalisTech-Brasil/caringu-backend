package tech.vitalis.caringu.dtos.PersonalTrainer;

import com.fasterxml.jackson.annotation.JsonInclude;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record PersonalTrainerResponsePatchDTO(
        Optional<String> nome,
        Optional<String> email,
        Optional<String> celular,
        Optional<String> urlFotoPerfil,
        Optional<LocalDate> dataNascimento,
        Optional<GeneroEnum> genero,

        Optional<String> cref,
        Optional<List<String>> especialidade,
        Optional<Integer> experiencia
) {
}
