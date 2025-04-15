package tech.vitalis.caringu.dtos.PersonalTrainer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.util.List;

public record PersonalTrainerRequestPatchDTO(
        @Schema(description = "Nome do aluno")
        String nome,

        @Email(message = "O email fornecido não é válido.")
        String email,

        @Size(min = 6, max = 16, message = "Senha deve conter entre 6 a 16 caracteres")
        String senha,

        @Size(max = 11, message = "O celular deve conter 11 dígitos")
        String celular,

        String urlFotoPerfil,

        @Past(message = "A data de nascimento deve ser no passado.")
        LocalDate dataNascimento,

        @Schema(description = "Gênero do aluno", allowableValues = {"MASCULINO", "FEMININO", "NAO_BINARIO", "OUTRO", "PREFIRO_NAO_INFORMAR"})
        GeneroEnum genero,

        @NotBlank(message = "O CREF é obrigatório")
        String cref,

        @NotEmpty(message = "É necessário informar ao menos uma especialidade")
        List<@NotBlank(message = "Especialidade não pode ser vazia") String> especialidade,

        @NotNull
        @Positive(message = "Os anos de experiência devem ser positivos.")
        Integer experiencia
) {
}
