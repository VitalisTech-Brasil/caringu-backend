package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Anamnese;

import java.util.Optional;

@Repository
public interface AnamneseRepository extends JpaRepository<Anamnese, Integer> {
    Optional<Anamnese> findByAlunoId(Integer id);

    boolean existsByAlunoId(Integer id);
}
