package tech.vitalis.caringu.exception.Pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class ContaBloqueadaException extends RuntimeException {
    private final long tempoRestante;

    public ContaBloqueadaException(String message, long tempoRestante) {
        super(message);
        this.tempoRestante = tempoRestante;
    }

    public long getTempoRestante() {
        return tempoRestante;
    }
}
