package tech.vitalis.caringu.exception.EvolucaoCorporal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EvolucaoCorporalNaoEncontradaException extends RuntimeException {
  public EvolucaoCorporalNaoEncontradaException(String message) {
    super(message);
  }
}
