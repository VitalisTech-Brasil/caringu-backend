package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

@Repository
public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Integer> {

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO(
        te.id,
        t.id,
        t.nome,
        te.grauDificuldade,
        te.favorito,
        te.origemTreinoExercicio,
        0
    )
    FROM TreinoExercicio te
    JOIN te.treinos t
    WHERE t.personal.id = :personalId
""")
    List<TreinoExercicioResumoDTO> findResumosSemQuantidade(@Param("personalId") Integer personalId);

    @Query("""
    SELECT te.treinos.id, COUNT(te)
    FROM TreinoExercicio te
    GROUP BY te.treinos.id
""")
    List<Object[]> countExerciciosPorTreino();

    boolean existsByTreinosAndExercicio_Id(Treino treinos, Integer exercicioId);

    boolean existsByTreinos_IdAndExercicio_Id(Integer treinosId, Integer exercicioId);

    List<TreinoExercicio> findByTreinos_Id(Integer treinosId);

    List<TreinoExercicio> findAllByTreinos_Id(Integer treinoId);
}
