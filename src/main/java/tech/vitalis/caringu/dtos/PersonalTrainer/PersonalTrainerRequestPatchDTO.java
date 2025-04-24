package tech.vitalis.caringu.dtos.PersonalTrainer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.util.List;

public record PersonalTrainerRequestPatchDTO(
        @NotBlank(message = "O nome é obrigatório.")
        @Schema(description = "Nome do personal trainer", example = "Roger A. Jones")
        String nome,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email fornecido não é válido.")
        @Schema(description = "E-mail do personal trainer", example = "roger.jones@gmail.com")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 16, message = "Senha deve conter entre 6 a 16 caracteres")
        @Schema(description = "Senha do personal trainer", example = "123Ab@")
        String senha,

        @Pattern(regexp = "\\d{11}", message = "O celular deve conter exatamente 11 dígitos numéricos")
        @Size(min = 11, max = 11, message = "O celular deve conter exatamente 11 dígitos")
        @Schema(description = "Celular do personal trainer", example = "11947139850")
        String celular,

        @Schema(description = "Endereço URL que aponta para a imagem de perfil hospedada em nuvem")
        String urlFotoPerfil,

        @Past(message = "A data de nascimento deve ser no passado.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de nascimento no formato ISO (ano-mês-dia)", example = "2000-04-17")
        LocalDate dataNascimento,

        @NotNull(message = "O gênero é obrigatório.")
        @Schema(description = "Gênero do personal trainer", allowableValues = {"MASCULINO", "FEMININO", "NAO_BINARIO", "OUTRO", "PREFIRO_NAO_INFORMAR"})
        GeneroEnum genero,

        @NotBlank(message = "O CREF é obrigatório")
        @Schema(
                description = "Registro profissional do personal trainer no CREF (Conselho Regional de Educação Física). Para mais informações: https://sistemacref4.com.br/crefonline/painel.do",
                example = "102464-G/SP"
        )
        String cref,

        @NotEmpty(message = "É necessário informar ao menos uma especialidade")
        @Schema(description = "Lista de especialidades")
        List<@NotBlank(message = "Especialidade não pode ser vazia") String> especialidade,

        @NotNull
        @Schema(description = "Anos de experiência do profissional")
        @Positive(message = "Os anos de experiência devem ser positivos.")
        @Max(value = 100, message = "A experiência não pode ser maior que 100 anos")
        Integer experiencia
) {
}
