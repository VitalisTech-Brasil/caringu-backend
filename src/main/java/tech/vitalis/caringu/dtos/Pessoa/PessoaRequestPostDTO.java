package tech.vitalis.caringu.dtos.Pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;

public record PessoaRequestPostDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Schema(description = "Nome da pessoa", example = "Roger A. Jones")
    String nome,

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email fornecido não é válido.")
    @Schema(description = "E-mail da pessoa", example = "roger.jones@gmail.com")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 16, message = "Senha deve conter entre 6 a 16 caracteres")
    @Schema(description = "Senha da pessoa", example = "123456")
    String senha,

    @Size(max = 11, message = "O celular deve conter 11 digitos")
    String celular,

    @Schema(description = "Endereço URL que aponta para a imagem de perfil hospedada em nuvem")
    String urlFotoPerfil,

    @Past(message = "A data de nascimento deve ser no passado.")
    LocalDate dataNascimento,

    @NotNull(message = "O gênero é obrigatório.")
    @Schema(description = "Gênero do aluno", allowableValues = {"MASCULINO", "FEMININO", "NAO_BINARIO", "OUTRO", "PREFIRO_NAO_INFORMAR"})
    GeneroEnum genero
) {

}
