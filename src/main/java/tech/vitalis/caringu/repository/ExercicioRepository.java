package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.vitalis.caringu.model.Exercicio;

public interface ExercicioRepository  extends JpaRepository<Exercicio, Long> {

}
