package tech.vitalis.caringu.h2Usuario.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//faz o getter setter, construtor, toString
public class CriacaoUsuarioDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;
}
