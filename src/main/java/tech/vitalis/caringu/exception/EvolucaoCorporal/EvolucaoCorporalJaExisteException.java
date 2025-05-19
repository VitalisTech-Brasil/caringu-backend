package tech.vitalis.caringu.exception.EvolucaoCorporal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EvolucaoCorporalJaExisteException extends RuntimeException {
    public EvolucaoCorporalJaExisteException(String message) {
        super(message);
    }
}
