package tech.vitalis.caringu.exception.Estado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EstadoJaExisteException extends RuntimeException {
    public EstadoJaExisteException(String message) {
        super(message);
    }
}
