package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

@Repository
public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Integer> {

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO(
        e.nome AS nome_exercicio,
        e.id AS exercicio_id,
        t.id AS treino_id,
        t.nome AS nomeTreino,
        te.grauDificuldade,
        te.favorito,
        te.origemTreinoExercicio
    )
    FROM TreinoExercicio te
    JOIN te.treinos t
    JOIN te.exercicio e
    WHERE t.personal.id = :personalId
""")
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorPersonal(@Param("personalId") Integer personalId);

    boolean existsByTreinosAndExercicio_Id(Treino treinos, Integer exercicioId);

    boolean existsByTreinos_IdAndExercicio_Id(Integer treinosId, Integer exercicioId);

    List<TreinoExercicio> findByTreinos_Id(Integer treinosId);

    List<TreinoExercicio> findAllByTreinos_Id(Integer treinoId);
}
