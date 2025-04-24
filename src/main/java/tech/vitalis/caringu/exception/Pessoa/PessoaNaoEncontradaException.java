package tech.vitalis.caringu.exception.Pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaNaoEncontradaException extends RuntimeException {
    public PessoaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}