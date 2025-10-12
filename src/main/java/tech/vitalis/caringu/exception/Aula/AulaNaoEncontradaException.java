package tech.vitalis.caringu.exception.Aula;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AulaNaoEncontradaException extends RuntimeException {
    public AulaNaoEncontradaException(String message) {
        super(message);
    }
}
