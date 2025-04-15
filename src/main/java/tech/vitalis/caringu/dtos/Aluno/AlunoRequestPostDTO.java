package tech.vitalis.caringu.dtos.Aluno;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

public record AlunoRequestPostDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email fornecido não é válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 16, message = "Senha deve conter entre 6 a 16 caracteres")
        String senha,

        @Size(max = 11, message = "O celular deve conter 11 digitos")
        String celular,

        String urlFotoPerfil,

        @Past(message = "A data de nascimento deve ser no passado.")
        LocalDate dataNascimento,

        @NotNull(message = "O gênero é obrigatório.")
        @Schema(description = "Gênero do aluno", allowableValues = {"MASCULINO", "FEMININO", "NAO_BINARIO", "OUTRO", "PREFIRO_NAO_INFORMAR"})
        GeneroEnum genero,

        @Positive(message = "O peso deve ser um número positivo.")
        Double peso,

        @Positive(message = "A altura deve ser um número positivo.")
        Double altura,

        @NotNull(message = "O nível de atividade é obrigatório.")
        @Schema(description = "Nível de atividade do aluno", allowableValues = {"SEDENTARIO", "LEVEMENTE_ATIVO", "MODERADAMENTE_ATIVO", "MUITO_ATIVO", "EXTREMAMENTE_ATIVO"})
        NivelAtividadeEnum nivelAtividade,

        @NotNull(message = "O nível de experiência é obrigatório.")
        @Schema(description = "Nível de experiência do aluno", allowableValues = {"INICIANTE", "INTERMEDIARIO", "AVANCADO"})
        NivelExperienciaEnum nivelExperiencia
) {

}
