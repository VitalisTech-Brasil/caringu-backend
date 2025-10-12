package tech.vitalis.caringu.exception.Aws;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArmazenamentoException extends RuntimeException{
    public ArmazenamentoException(String message) {
        super(message);
    }
    public ArmazenamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
