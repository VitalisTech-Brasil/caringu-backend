package tech.vitalis.caringu.exception.Anamnese;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnamneseNaoEncontradaException extends RuntimeException {
    public AnamneseNaoEncontradaException(String message) {
        super(message);
    }
}