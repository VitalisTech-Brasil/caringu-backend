package tech.vitalis.caringu.exception.PersonalTrainerBairro;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AssociacaoJaExisteException extends ResponseStatusException {
  public AssociacaoJaExisteException() {
    super(HttpStatus.CONFLICT, "Essa associação entre Personal Trainer e Bairro já existe.");
  }
}
