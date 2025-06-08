package tech.vitalis.caringu.enums;

public enum PeriodoEnum {
    AVULSO ("Avulso"),
    MENSAL ("Mensal"),
    SEMESTRAL("Semestral");

    private final String descricao;

    PeriodoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
