package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.entity.Especialidade;
import tech.vitalis.caringu.entity.ExecucaoExercicio;

import java.util.List;
import java.util.Optional;

public interface ExecucaoExercicioRepository extends JpaRepository<ExecucaoExercicio, Integer> {
    void deleteByAulaTreinoExercicioIdIn(List<Integer> ids);
}
