package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoIdentificacaoFinalizadoResponseDTO;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.entity.TreinoFinalizado;

import java.util.List;

@Repository
public interface TreinoFinalizadoRepository extends JpaRepository<TreinoFinalizado, Integer> {

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', tf.dataHorarioInicio, '%Y-%m-%d %H:%i')
    FROM TreinoFinalizado tf
    WHERE tf.alunoTreino.alunos.id = :alunoId
      AND FUNCTION('YEARWEEK', tf.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    ORDER BY tf.dataHorarioInicio
""")
    List<String> buscarHorariosInicioSemana(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', tf.dataHorarioFim, '%Y-%m-%d %H:%i')
    FROM TreinoFinalizado tf
    WHERE tf.alunoTreino.alunos.id = :alunoId
      AND FUNCTION('YEARWEEK', tf.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    ORDER BY tf.dataHorarioInicio
""")
    List<String> buscarHorariosFimSemana(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', tf.dataHorarioInicio, '%Y-%m-%d %H:%i')
    FROM TreinoFinalizado tf
    WHERE tf.alunoTreino.alunos.id = :alunoId
    ORDER BY tf.dataHorarioInicio
""")
    List<String> buscarHorariosInicioTotal(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', tf.dataHorarioFim, '%Y-%m-%d %H:%i')
    FROM TreinoFinalizado tf
    WHERE tf.alunoTreino.alunos.id = :alunoId
    ORDER BY tf.dataHorarioInicio
""")
    List<String> buscarHorariosFimTotal(@Param("alunoId") Integer alunoId);

    @Query("""
        SELECT new tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoIdentificacaoFinalizadoResponseDTO(
            tf.id,
            tf.dataHorarioInicio,
            tf.dataHorarioFim,
            a.id,
            a.nome,
            a.urlFotoPerfil,
            CASE WHEN tf.dataHorarioFim IS NOT NULL THEN true ELSE false END
        )
        FROM TreinoFinalizado tf
        JOIN tf.alunoTreino at
        JOIN at.alunos a
        JOIN PlanoContratado pc ON pc.aluno.id = a.id
        JOIN pc.plano pl
        WHERE pc.status = 'ATIVO'
        AND pl.personalTrainer.id = :personalId
    """)
    List<TreinoIdentificacaoFinalizadoResponseDTO> findAllTreinosByPersonalId(@Param("personalId") Integer personalId);
}
