package tech.vitalis.caringu.exception.PlanoContratado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AlunoSemPlanoContratadoException extends RuntimeException {
    public AlunoSemPlanoContratadoException(String message) {
        super(message);
    }
}
