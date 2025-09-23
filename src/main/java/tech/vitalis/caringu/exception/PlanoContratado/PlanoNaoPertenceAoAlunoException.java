package tech.vitalis.caringu.exception.PlanoContratado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PlanoNaoPertenceAoAlunoException extends RuntimeException {
    public PlanoNaoPertenceAoAlunoException(String message) {
        super(message);
    }
}
