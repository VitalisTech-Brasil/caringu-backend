package tech.vitalis.caringu.exception.Bairro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BairroNaoEncontradoException extends RuntimeException {
    public BairroNaoEncontradoException(String message) {
        super(message);
    }
}
