package tech.vitalis.caringu.enums.Exercicio;

public enum GrupoMuscularEnum {

    PEITORAL("Peitoral"),
    COSTAS("Costas"),
    PERNAS("Pernas"),
    OMBRO("Ombro"),
    BRACO("Braço"),
    CORE("Core"),
    CARDIO("Cardio");

    private String value;

    GrupoMuscularEnum(){}

    GrupoMuscularEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
