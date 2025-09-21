package tech.vitalis.caringu.dtos.Pessoa.security;

public class TentativaDeLogin {
    private Integer contador = 0;
    private long bloqueadoAte;

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public long getBloqueadoAte() {
        return bloqueadoAte;
    }

    public void setBloqueadoAte(long bloqueadoAte) {
        this.bloqueadoAte = bloqueadoAte;
    }
}
