package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.entity.TreinoFinalizado;

import java.util.List;

@Repository
public interface TreinoFinalizadoRepository extends JpaRepository<TreinoFinalizado, Integer> {
    @Query("""
    SELECT DATE_FORMAT(tf.dataHorarioInicio, '%Y-%m-%d')
    FROM TreinoFinalizado tf
    WHERE tf.alunoTreino.alunos.id = :alunoId
      AND FUNCTION('YEARWEEK', tf.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
""")
    List<String> buscarDatasTreinosSemana(@Param("alunoId") Integer alunoId);
}
