package tech.vitalis.caringu.exception.AulaTreinoExercicio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AulaTreinoExercicioNaoEncontradaException extends RuntimeException {
    public AulaTreinoExercicioNaoEncontradaException(String message) {
        super(message);
    }
}
