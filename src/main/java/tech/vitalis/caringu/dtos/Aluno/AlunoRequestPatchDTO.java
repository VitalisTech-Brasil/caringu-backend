package tech.vitalis.caringu.dtos.Aluno;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

public record AlunoRequestPatchDTO(
        @NotBlank(message = "O nome é obrigatório.")
        @Schema(description = "Nome do aluno", example = "Roger A. Jones")
        String nome,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email fornecido não é válido.")
        @Schema(description = "E-mail do aluno", example = "roger.jones@gmail.com")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 16, message = "Senha deve conter entre 6 a 16 caracteres")
        @Schema(description = "Senha do aluno", example = "123Ab@")
        String senha,

        @Pattern(regexp = "\\d{11}", message = "O celular deve conter exatamente 11 dígitos numéricos")
        @Size(min = 11, max = 11, message = "O celular deve conter exatamente 11 dígitos")
        @Schema(description = "Celular do aluno", example = "11947139850")
        String celular,

        @Schema(description = "Endereço URL que aponta para a imagem de perfil hospedada em nuvem")
        String urlFotoPerfil,

        @Past(message = "A data de nascimento deve ser no passado.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de nascimento no formato ISO (ano-mês-dia)", example = "2000-04-17")
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