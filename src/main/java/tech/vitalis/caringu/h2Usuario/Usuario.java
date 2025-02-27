package tech.vitalis.caringu.h2Usuario;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tech.vitalis.caringu.h2Usuario.dtos.CriacaoUsuarioDTO;
import tech.vitalis.caringu.h2Usuario.dtos.RespostaUsuarioDTO;

@Entity
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    public Usuario() {
    }

    public Usuario(CriacaoUsuarioDTO dtoCriacao) {
        this.nome = dtoCriacao.getNome();
        this.email = dtoCriacao.getEmail();
        this.senha = dtoCriacao.getSenha();
    }

}
