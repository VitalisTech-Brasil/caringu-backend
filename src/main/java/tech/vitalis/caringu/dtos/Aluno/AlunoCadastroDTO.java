package tech.vitalis.caringu.dtos.Aluno;

import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.util.Date;

public record AlunoCadastroDTO(
        @NotBlank String nome,

        @NotBlank
        @Email
        String email,

        @Size(min = 6, max = 16)
        @NotBlank
        String senha,

        @NotBlank String celular,

        @Past
        @NotNull
        Date dataNascimento,

        @NotNull GeneroEnum genero,

        @Positive Double peso,

        @Positive Double altura,

        @NotNull NivelAtividadeEnum nivelAtividade,

        @NotNull NivelExperienciaEnum nivelExperiencia
) {

}
