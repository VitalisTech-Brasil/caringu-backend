package tech.vitalis.caringu.exception.Especialidade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EspecialidadeNaoEncontrada extends RuntimeException {
    public EspecialidadeNaoEncontrada(String message) {
        super(message);
    }
}
