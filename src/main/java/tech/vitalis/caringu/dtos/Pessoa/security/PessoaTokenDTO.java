package tech.vitalis.caringu.dtos.Pessoa.security;

public class PessoaTokenDTO {
    private Integer pessoaId;
    private String nome;
    private String email;
    private String token;
    private String tipo; // ALUNO ou PERSONAL

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
