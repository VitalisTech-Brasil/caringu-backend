package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.TreinoFinalizado.EvolucaoCargaDashboardResponseDTO;
import tech.vitalis.caringu.dtos.TreinoFinalizado.EvolucaoTreinoCumpridoResponseDTO;
import tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoIdentificacaoFinalizadoResponseDTO;
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

    @Query("""
        SELECT new tech.vitalis.caringu.dtos.TreinoFinalizado.EvolucaoCargaDashboardResponseDTO(
          a.id, a.nome, a.peso, a.altura,
          tf.dataHorarioInicio, tf.dataHorarioFim,
          te.carga, ex.nome
        )
        FROM TreinoFinalizado tf
        JOIN tf.alunoTreino at
        JOIN at.treinosExercicios te
        JOIN te.exercicio ex
        JOIN at.alunos a
        WHERE ex.id = :exercicioId AND a.id = :alunoId
        ORDER BY a.nome, tf.dataHorarioInicio
    """)
    List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(@Param("exercicioId") Integer exercicioId, @Param("alunoId") Integer alunoId);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.TreinoFinalizado.EvolucaoTreinoCumpridoResponseDTO(
        a.id,
        a.nome,
        e.id,
        e.nome,
        YEAR(tf.dataHorarioInicio),
        MONTH(tf.dataHorarioInicio),
        COUNT(tf.id),
        ana.frequenciaTreino
    )
    FROM Aluno a
    LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
    JOIN AlunoTreino at ON at.alunos.id = a.id
    JOIN TreinoExercicio te ON at.treinosExercicios.id = te.id
    JOIN Exercicio e ON te.exercicio.id = e.id
    JOIN TreinoFinalizado tf ON tf.alunoTreino.id = at.id
    WHERE a.id = :alunoId
      AND e.id = :exercicioId
    GROUP BY 
        a.id, a.nome, e.id, e.nome, YEAR(tf.dataHorarioInicio), MONTH(tf.dataHorarioInicio), ana.frequenciaTreino
    ORDER BY YEAR(tf.dataHorarioInicio), MONTH(tf.dataHorarioInicio)
""")
    List<EvolucaoTreinoCumpridoResponseDTO> buscarEvolucaoTreinosCumpridosMensal(
            @Param("alunoId") Integer alunoId,
            @Param("exercicioId") Integer exercicioId
    );

    @Query(value = """
        SELECT 
            a.id AS idAluno,
            p.nome AS nomeAluno,
            ex.id AS idExercicio,
            ex.nome AS nomeExercicio,
            YEAR(tf.data_horario_inicio) AS ano,
            MONTH(tf.data_horario_inicio) AS mes,
            YEARWEEK(tf.data_horario_inicio, 1) AS anoSemana,
            ROUND(SUM(TIMESTAMPDIFF(MINUTE, tf.data_horario_inicio, tf.data_horario_fim)) / 60, 2) AS horasTreinadas
        FROM treinos_finalizados tf
        JOIN alunos_treinos at ON tf.alunos_treinos_id = at.id
        JOIN treinos_exercicios te ON at.treinos_exercicios_id = te.id
        JOIN exercicios ex ON te.exercicio_id = ex.id
        JOIN alunos a ON at.alunos_id = a.id
        JOIN pessoas p ON a.id = p.id
        WHERE tf.data_horario_inicio <= NOW()
          AND ex.id = :exercicioId
          AND a.id = :alunoId
        GROUP BY 
            a.id, 
            ex.id, 
            YEAR(tf.data_horario_inicio), 
            MONTH(tf.data_horario_inicio), 
            YEARWEEK(tf.data_horario_inicio, 1)
        ORDER BY ano, mes, anoSemana
        """, nativeQuery = true)
    List<Object[]> buscarHorasAgrupadasPorAlunoExercicio(
            @Param("alunoId") Integer alunoId,
            @Param("exercicioId") Integer exercicioId
    );
}
