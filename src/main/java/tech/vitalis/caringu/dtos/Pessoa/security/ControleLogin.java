package tech.vitalis.caringu.dtos.Pessoa.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ControleLogin {
    private final ConcurrentMap<String, TentativaDeLogin> cache = new ConcurrentHashMap<>();
    private static final int tentativasMax = 5;

    //    pra uso ( 15min )
    private static final long duracaoBlock = 15 * 60 * 1000;

//    pra teste ( 5s )
//    private static final long duracaoBlock = 5 * 1000;

    // ver se o usuario está bloqueado
    public boolean validarBloqueio(String email) {
        TentativaDeLogin tentativa = cache.get(email);
        if (tentativa == null) return false;
        long tempoRealAtual = System.currentTimeMillis();
        if (tentativa.getBloqueadoAte() > 0 && tentativa.getBloqueadoAte() <= tempoRealAtual) {
            cache.remove(email);
            return false;
        }
        return tentativa.getBloqueadoAte() > tempoRealAtual;
    }

    // se ele tentar e errar, chama esse pra registrar
    public boolean registrarFalha(String email) {
        TentativaDeLogin tentativa = cache.computeIfAbsent(email, k -> new TentativaDeLogin());
        synchronized (tentativa) {
            long tempoRealAtual = System.currentTimeMillis();
            if (tentativa.getBloqueadoAte() > 0 && tentativa.getBloqueadoAte() <= tempoRealAtual) {
                tentativa.setContador(0);
                tentativa.setBloqueadoAte(0);
            }
            tentativa.setContador(tentativa.getContador() + 1);

            if (tentativa.getContador() >= tentativasMax) {
                tentativa.setBloqueadoAte(tempoRealAtual + duracaoBlock);
                return true;
            }
            return false;
        }
    }

    // remove ele do cache se acertar e logar com sucesso
    public void registrarSucesso(String username) {
        cache.remove(username);
    }

    // ve qto tempo falta pra desbloquear
    public long tempoRestante(String email) {
        long tempoRealAtual = System.currentTimeMillis();
        TentativaDeLogin tentativa = cache.get(email);
        if (tentativa == null || tentativa.getBloqueadoAte() <= tempoRealAtual){
            return 0;
        }
        return (tentativa.getBloqueadoAte() - tempoRealAtual) / 1000;
    }
}
