package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.vitalis.caringu.entity.AlunoTreinoExercicio;

import java.util.List;

public interface AlunoTreinoExercicioRepository extends JpaRepository<AlunoTreinoExercicio, Integer> {

    List<AlunoTreinoExercicio> findAllByExercicioId(Integer id);
}
