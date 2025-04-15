package tech.vitalis.caringu.dtos.Aluno;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

public record AlunoRequestPatchDTO(

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

        @Positive(message = "O peso deve ser um número positivo.")
        Double peso,

        @Positive(message = "A altura deve ser um número positivo.")
        Double altura,

        @Schema(description = "Nível de atividade do aluno", allowableValues = {"SEDENTARIO", "LEVEMENTE_ATIVO", "MODERADAMENTE_ATIVO", "MUITO_ATIVO", "EXTREMAMENTE_ATIVO"})
        NivelAtividadeEnum nivelAtividade,

        @Schema(description = "Nível de experiência do aluno", allowableValues = {"INICIANTE", "INTERMEDIARIO", "AVANCADO"})
        NivelExperienciaEnum nivelExperiencia
) {
}