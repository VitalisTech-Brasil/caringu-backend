package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsByEmail(String email);
}
