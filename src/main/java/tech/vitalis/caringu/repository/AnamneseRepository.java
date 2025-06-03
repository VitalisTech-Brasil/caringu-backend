package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Anamnese;

import java.util.Optional;

@Repository
public interface AnamneseRepository extends JpaRepository<Anamnese, Integer> {

    Optional<Anamnese> findByAlunoId(Integer alunoId);
    boolean existsByAlunoId(Integer id);
    @Query("""
    SELECT COUNT(DISTINCT a)
    FROM Aluno a
    LEFT JOIN Anamnese an ON an.aluno.id = a.id
    JOIN PlanoContratado pc ON pc.aluno.id = a.id
    JOIN Plano pl ON pc.plano.id = pl.id
    WHERE an.id IS NULL AND pc.status = "ATIVO" AND pl.personalTrainer.id = :personalId
""")
    Integer countAnamnesesPendentesByPersonalId(@Param("personalId") Integer personalId);

}
