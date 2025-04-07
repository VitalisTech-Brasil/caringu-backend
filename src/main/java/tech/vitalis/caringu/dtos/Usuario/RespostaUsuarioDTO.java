package tech.vitalis.caringu.dtos.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tech.vitalis.caringu.entity.Usuario;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespostaUsuarioDTO {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

}
