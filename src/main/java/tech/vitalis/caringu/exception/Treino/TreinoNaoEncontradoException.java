package tech.vitalis.caringu.exception.Treino;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TreinoNaoEncontradoException extends RuntimeException {
    public TreinoNaoEncontradoException(String message) {
        super(message);
    }
}
