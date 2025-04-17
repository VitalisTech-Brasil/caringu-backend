package tech.vitalis.caringu.dtos.Pessoa.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public class PessoaLoginDTO {

    @Email
    @Schema(description = "E-mail da pessoa", example = "roger.jones@gmail.com")
    private String email;

    @Schema(description = "Senha da pessoa", example = "123456")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
