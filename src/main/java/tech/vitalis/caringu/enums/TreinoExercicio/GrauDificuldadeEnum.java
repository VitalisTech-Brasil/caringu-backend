package tech.vitalis.caringu.enums.TreinoExercicio;

public enum GrauDificuldadeEnum {
    INICIANTE("Iniciante"),
    INTERMEDIARIO("Intermediário"),
    AVANCADO("Avançado");

    private String valor;

    GrauDificuldadeEnum(String valor) {
        this.valor = valor;
    }

    GrauDificuldadeEnum(){}

    public String getValor() {
        return valor;
    }
}
