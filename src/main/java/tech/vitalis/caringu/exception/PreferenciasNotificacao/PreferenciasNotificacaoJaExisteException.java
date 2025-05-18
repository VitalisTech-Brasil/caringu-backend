package tech.vitalis.caringu.exception.PreferenciasNotificacao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PreferenciasNotificacaoJaExisteException extends RuntimeException {
    public PreferenciasNotificacaoJaExisteException(String message) {
        super(message);
    }
}
