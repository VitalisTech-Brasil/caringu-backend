package tech.vitalis.caringu.exception.Anamnese;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AnamneseJaCadastradaException extends RuntimeException {
    public AnamneseJaCadastradaException(String message) {
        super(message);
    }
}