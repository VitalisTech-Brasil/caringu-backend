package tech.vitalis.caringu.enums.Feedback;

public enum TipoAutorEnum {
    PERSONAL("Personal"),
    ALUNO("Aluno");

    private String value;

    TipoAutorEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
