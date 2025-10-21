package tech.vitalis.caringu.core.domain.valueObject;

public enum FrequenciaTreinoEnum {
    UM("1"),
    DOIS("2"),
    TRES("3"),
    QUATRO("4"),
    CINCO("5"),
    SEIS("6"),
    SETE("7");

    private final String valor;

    FrequenciaTreinoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static FrequenciaTreinoEnum fromValor(String valor) {
        for (FrequenciaTreinoEnum f : values()) {
            if (f.valor.equalsIgnoreCase(valor)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Valor inválido para FrequenciaTreino: " + valor);
    }

    @Override
    public String toString(){
        return valor;
    }
}
