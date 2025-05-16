package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.vitalis.caringu.entity.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {
}
