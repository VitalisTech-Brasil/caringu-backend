package tech.vitalis.caringu.exception.PlanoContratado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanoContratadoNaoEncontradoException extends RuntimeException {
    public PlanoContratadoNaoEncontradoException(String message) {
        super(message);
    }
}
