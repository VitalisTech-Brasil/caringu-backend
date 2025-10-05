package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;

import java.util.List;

public interface AulaTreinoExercicioRepository extends JpaRepository<AulaTreinoExercicio, Integer> {

    @Query("""
                SELECT
                    COALESCE(MAX(ate.ordem), 0)
                FROM AulaTreinoExercicio ate
                WHERE ate.aula.id = :idAula
            """)
    Integer findMaxOrdemByAulaId(@Param("idAula") Integer idAula);

    List<AulaTreinoExercicio> findByAulaId(Integer aulaId);
}
