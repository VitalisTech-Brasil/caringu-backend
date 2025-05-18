package tech.vitalis.caringu.exception.PreferenciasNotificacao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PreferenciasNotificacaoNaoEncontradaException extends RuntimeException {
  public PreferenciasNotificacaoNaoEncontradaException(String message) {
    super(message);
  }
}
