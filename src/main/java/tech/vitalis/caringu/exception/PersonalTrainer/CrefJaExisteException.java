package tech.vitalis.caringu.exception.PersonalTrainer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CrefJaExisteException extends RuntimeException {
    public CrefJaExisteException(String message) {
        super(message);
    }
}
