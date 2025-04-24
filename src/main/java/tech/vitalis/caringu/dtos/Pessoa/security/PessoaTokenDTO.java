package tech.vitalis.caringu.dtos.Pessoa.security;

import io.swagger.v3.oas.annotations.media.Schema;

public class PessoaTokenDTO {

    @Schema(description = "ID da pessoa autenticada", example = "42")
    private Integer pessoaId;

    @Schema(description = "Nome completo da pessoa", example = "João Silva")
    private String nome;

    @Schema(description = "E-mail da pessoa", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Token JWT de autenticação da pessoa")
    private String token;

    @Schema(description = "Tipo da pessoa autenticada", allowableValues = {"PERSONAL", "ALUNO"}, example = "ALUNO")
    private String tipo;

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
