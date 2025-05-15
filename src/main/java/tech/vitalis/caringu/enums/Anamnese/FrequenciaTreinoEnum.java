package tech.vitalis.caringu.enums.Anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public String getValor() {
        return valor;
    }

    @JsonCreator
    public static FrequenciaTreinoEnum fromValor(String valor) {
        for (FrequenciaTreinoEnum f : values()) {
            if (f.valor.equals(valor)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Valor inválido para FrequenciaTreino: " + valor);
    }
}
