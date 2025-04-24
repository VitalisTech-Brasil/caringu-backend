package tech.vitalis.caringu.exception.Pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
