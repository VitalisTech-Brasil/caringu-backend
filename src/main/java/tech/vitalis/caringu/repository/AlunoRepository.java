package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Aluno;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("""
    SELECT a
    FROM Aluno a
    LEFT JOIN Anamnese an ON a.id = an.aluno.id
    WHERE an.id IS NULL
""")
    List<Aluno> findAlunosSemAnamnese();
}
