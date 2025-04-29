package tech.vitalis.caringu.dtos.EsqueciSenha;

public class EsqueciSenhaCodigoDto {
    private String email;
    private String codigo;

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
