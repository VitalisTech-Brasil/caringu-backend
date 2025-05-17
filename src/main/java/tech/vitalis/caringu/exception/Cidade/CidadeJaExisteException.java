package tech.vitalis.caringu.exception.Cidade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CidadeJaExisteException extends RuntimeException {
    public CidadeJaExisteException(String message) {
        super(message);
    }
}
