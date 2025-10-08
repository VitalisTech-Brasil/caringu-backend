package tech.vitalis.caringu.exception.Aula;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AulaConflitanteException extends RuntimeException {
    public AulaConflitanteException(String message) {
        super(message);
    }
}
