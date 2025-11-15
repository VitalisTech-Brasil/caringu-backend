package tech.vitalis.caringu.dtos.Pessoa.security.strategy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Pessoa.security.TentativaDeLogin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Profile({"dev", "!dev-with-redis"})
public class ControleLoginMemoria implements ControleLogin {

    private final Map<String, TentativaDeLogin> mapa = new ConcurrentHashMap<>();

    @Override
    public boolean validarBloqueio(String email) {
        var t = mapa.get(email);
        return t != null && System.currentTimeMillis() < t.getBloqueadoAte();
    }

    @Override
    public long tempoRestante(String email) {
        var t = mapa.get(email);
        if (t == null) return 0;
        return (t.getBloqueadoAte() - System.currentTimeMillis()) / 1000;
    }

    @Override
    public boolean registrarFalha(String email) {
        var t = mapa.computeIfAbsent(email, TentativaDeLogin::new);
        t.setContador(t.getContador() + 1);

        if (t.getContador() >= 5) {
            t.setBloqueadoAte(System.currentTimeMillis() + 15 * 60 * 1000);
            return true;
        }

        return false;
    }

    @Override
    public void registrarSucesso(String email) {
        mapa.remove(email);
    }
}