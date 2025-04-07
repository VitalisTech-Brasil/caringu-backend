package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.AlunoTreino;

@Repository
public interface AlunoTreinoRepository extends JpaRepository<AlunoTreino, Integer> {
}
