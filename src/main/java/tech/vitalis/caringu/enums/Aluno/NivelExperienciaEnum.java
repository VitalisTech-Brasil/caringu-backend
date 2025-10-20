package tech.vitalis.caringu.enums.Aluno;

public enum NivelExperienciaEnum {

    INICIANTE("Iniciante"),
    INTERMEDIARIO("Intermediário"),
    AVANCADO("Avançado");

    private String valor;

    public String getValor() {
        return valor;
    }

    NivelExperienciaEnum(String valor) {
        this.valor = valor;
    }

    NivelExperienciaEnum() {
    }
}
