package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Anamnese;

import java.util.Optional;

@Repository
public interface AnamneseRepository extends JpaRepository<Anamnese, Integer> {

    Optional<Anamnese> findByAlunoId(Integer id);
    boolean existsByAlunoId(Integer id);
    @Query("""
    SELECT COUNT(a)
    FROM Anamnese a
    JOIN PlanoContratado pc ON pc.aluno = a.aluno
    JOIN Plano p ON pc.plano = p
    WHERE p.personalTrainer.id = :personalId
""")
    Integer countAnamnesesPendentesByPersonalId(@Param("personalId") Integer personalId);
}
