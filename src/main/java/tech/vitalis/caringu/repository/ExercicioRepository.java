package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Exercicio;

@Repository
public interface ExercicioRepository  extends JpaRepository<Exercicio, Integer> {

}
