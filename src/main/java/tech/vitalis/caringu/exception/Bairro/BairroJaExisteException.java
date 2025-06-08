package tech.vitalis.caringu.exception.Bairro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BairroJaExisteException extends RuntimeException {
    public BairroJaExisteException(String message) {
        super(message);
    }
}
