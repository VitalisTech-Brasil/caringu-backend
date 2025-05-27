package tech.vitalis.caringu.exception.TreinoFinalizado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TreinoFinalizadoNaoEncontradoException extends RuntimeException {
  public TreinoFinalizadoNaoEncontradoException(String message) {
    super(message);
  }
}
