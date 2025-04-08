package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.TreinoExercicio;

@Repository
public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Integer> {
}
