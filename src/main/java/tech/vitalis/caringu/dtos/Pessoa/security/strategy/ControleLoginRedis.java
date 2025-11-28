package tech.vitalis.caringu.dtos.Pessoa.security.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Pessoa.security.TentativaDeLogin;
import tech.vitalis.caringu.repository.redis.RedisRepository;

@Service
@Primary
@Profile({"dev-with-redis", "prod"})
public class ControleLoginRedis implements ControleLogin {

    private final RedisRepository redisRepository;

    private static final int tentativasMax = 5;
    private static final long duracaoBlock = 15 * 60 * 1000;

    public ControleLoginRedis(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    // ver se o usuario está bloqueado
    public boolean validarBloqueio(String email) {
        TentativaDeLogin tentativa = redisRepository.findById(email).orElse(null);

        if (tentativa == null) return false;

        long tempoRealAtual = System.currentTimeMillis();

        if (tentativa.getBloqueadoAte() > 0 && tentativa.getBloqueadoAte() <= tempoRealAtual) {
            redisRepository.deleteById(email);
            return false;
        }

        return tentativa.getBloqueadoAte() > tempoRealAtual;
    }

    // se ele tentar e errar, chama esse pra registrar
    public boolean registrarFalha(String email) {
        TentativaDeLogin tentativa =
                redisRepository.findById(email).orElse(
                        new TentativaDeLogin(email)
                );

        long agora = System.currentTimeMillis();

        if (tentativa.getBloqueadoAte() > 0 && tentativa.getBloqueadoAte() <= agora) {
            tentativa.setContador(0);
            tentativa.setBloqueadoAte(0);
        }

        tentativa.setContador(tentativa.getContador() + 1);

        if (tentativa.getContador() >= tentativasMax) {
            tentativa.setBloqueadoAte(agora + duracaoBlock);
            redisRepository.save(tentativa);
            return true;
        }

        redisRepository.save(tentativa);
        return false;
    }

    // remove ele do cache se acertar e logar com sucesso
    public void registrarSucesso(String username) {
        redisRepository.deleteById(username);
    }

    // ve qto tempo falta pra desbloquear
    public long tempoRestante(String email) {
        TentativaDeLogin tentativa = redisRepository.findById(email).orElse(null);
        if (tentativa == null) return 0;

        long agora = System.currentTimeMillis();
        if (tentativa.getBloqueadoAte() <= agora) return 0;

        return (tentativa.getBloqueadoAte() - agora) / 1000;
    }
}
