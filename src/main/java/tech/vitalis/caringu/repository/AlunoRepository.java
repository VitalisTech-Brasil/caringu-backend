package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Aluno.AlunoAtivoResponseDTO;
import tech.vitalis.caringu.dtos.Aluno.PresencaAlunoResponseDTO;
import tech.vitalis.caringu.entity.Aluno;

import java.time.LocalDateTime;
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

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Aluno.AlunoAtivoResponseDTO(
        a.id,
        a.nome,
        a.celular,
        an.objetivoTreino,
        CASE WHEN (SELECT COUNT(an) FROM Anamnese an WHERE an.aluno.id = a.id) > 0 THEN true ELSE false END,
        CASE WHEN (SELECT COUNT(t) FROM AlunoTreino t WHERE t.alunos.id = a.id AND t.dataHorarioFim IS NULL) > 0 THEN true ELSE false END
    )
    FROM Aluno a
    LEFT JOIN Anamnese an
    ON an.aluno.id = a.id
    WHERE EXISTS (
        SELECT 1 FROM PlanoContratado pc
        WHERE pc.aluno.id = a.id 
            AND pc.status = 'ATIVO'
            AND pc.plano.personalTrainer.id = :personalId
    )
""")
    List<AlunoAtivoResponseDTO> buscarAlunosAtivosComIndicadores(@Param("personalId") Integer personalId);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Aluno.PresencaAlunoResponseDTO(
        a.id,
        p.nome,
        p.urlFotoPerfil,
        COUNT(at.id),
        an.frequenciaTreino
    )
    FROM Aluno a
    JOIN Pessoa p ON p.id = a.id
    JOIN PlanoContratado pc ON pc.aluno.id = a.id
    JOIN Plano pl ON pc.plano.id = pl.id
    LEFT JOIN AlunoTreino at ON at.alunos.id = a.id
        AND at.dataHorarioFim IS NOT NULL
        AND at.dataHorarioFim BETWEEN :inicio AND :fim
    JOIN Anamnese an ON an.aluno.id = a.id
    WHERE pl.personalTrainer.id = :personalId
      AND pc.status = 'ATIVO'
    GROUP BY a.id, p.nome, p.urlFotoPerfil, an.frequenciaTreino
    ORDER BY p.nome
""")
    List<PresencaAlunoResponseDTO> buscarPresencaAlunos(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("personalId") Integer personalId
    );
}
