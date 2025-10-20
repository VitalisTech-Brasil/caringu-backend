package tech.vitalis.caringu.enums.Pessoa;

public enum GeneroEnum {
    HOMEM_CISGENERO("Homem Cisgênero"),
    HOMEM_TRANSGENERO("Homem Transgênero"),
    MULHER_CISGENERO("Mulher Cisgênero"),
    MULHER_TRANSGENERO("Mulher Transgênero"),
    NAO_BINARIO("Não Binário");

    private String valor;

    public String getValor() {
        return valor;
    }

    GeneroEnum(String valor) {
        this.valor = valor;
    }

    GeneroEnum() {
    }

}
