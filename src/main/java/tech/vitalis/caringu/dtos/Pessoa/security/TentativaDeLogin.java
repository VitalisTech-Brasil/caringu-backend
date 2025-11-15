package tech.vitalis.caringu.dtos.Pessoa.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("tentativas_login")
public class TentativaDeLogin {

    @Id
    private String email;

    private Integer contador = 0;
    private long bloqueadoAte;

    public TentativaDeLogin() {
    }

    public TentativaDeLogin(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
