package tech.vitalis.caringu.exception.Cidade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CidadeNaoEncontradaException extends RuntimeException {
    public CidadeNaoEncontradaException(String message) {
        super(message);
    }
}
