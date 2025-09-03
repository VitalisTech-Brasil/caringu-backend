package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.AlunoTreinoExercicio;

import java.util.List;

public interface AlunoTreinoExercicioRepository extends JpaRepository<AlunoTreinoExercicio, Integer> {

    List<AlunoTreinoExercicio> findAllByExercicioId(Integer id);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO(
                	e.nome,
                	e.id,
                	t.id,
                	t.nome,
                	t.grauDificuldade,
                	t.origem,
                	t.favorito
                )
                FROM AlunoTreinoExercicio ate
                join Treino t ON ate.treino.id = t.id
                join Exercicio e ON ate.exercicio.id = e.id
                join AlunoTreino at ON ate.alunoTreino.id = at.id
                where at.alunos.id = 6
            """)
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorAluno(@Param("alunoId") Integer alunoId);
}
