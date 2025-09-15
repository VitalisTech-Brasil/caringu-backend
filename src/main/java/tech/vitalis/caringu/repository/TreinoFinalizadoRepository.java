package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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

//    @Query("""
//        SELECT new tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoIdentificacaoFinalizadoResponseDTO(
//            tf.id,
//            tf.dataHorarioInicio,
//            tf.dataHorarioFim,
//            a.id,
//            a.nome,
//            a.urlFotoPerfil,
//            CASE WHEN tf.dataHorarioFim IS NOT NULL THEN true ELSE false END
//        )
//        FROM TreinoFinalizado tf
//        JOIN tf.alunoTreino at
//        JOIN at.alunos a
//        JOIN PlanoContratado pc ON pc.aluno.id = a.id
//        JOIN pc.plano pl
//        WHERE pc.status = 'ATIVO'
//        AND pl.personalTrainer.id = :personalId
//    """)
//    List<TreinoIdentificacaoFinalizadoResponseDTO> findAllTreinosByPersonalId(@Param("personalId") Integer personalId);

//    @Query("""
//    SELECT new tech.vitalis.caringu.dtos.TreinoFinalizado.EvolucaoCargaDashboardResponseDTO(
//        a.id,
//        p.nome,
//        a.peso,
//        a.altura,
//        st.dataHorarioInicio,
//        st.dataHorarioFim,
//        ee.cargaExecutada,
//        ex.nome
//    )
//    FROM SessaoTreino st
//    JOIN AlunoTreino at ON st.alunoTreino.id = at.id
//    JOIN ExecucaoExercicio ee ON st.id = ee.sessaoTreino.id
//    JOIN AlunoTreinoExercicio ate ON ate.id = ee.alunoTreinoExercicio.id
//    JOIN Exercicio ex ON ate.exercicio.id = ex.id
//    JOIN Aluno a ON at.alunos.id = a.id
//    JOIN Pessoa p ON a.id = p.id
//    WHERE ex.id = :idExercicio
//      AND a.id = :idAluno
//      AND st.status = 'REALIZADO'
//    ORDER BY p.nome, st.dataHorarioInicio
//""")
//    List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(
//            @Param("idExercicio") Integer idExercicio,
//            @Param("idAluno") Integer idAluno
//    );

//    @Query("""
//    SELECT new tech.vitalis.caringu.dtos.TreinoFinalizado.EvolucaoTreinoCumpridoResponseDTO(
//        a.id,
//        a.nome,
//        e.id,
//        e.nome,
//        YEAR(tf.dataHorarioInicio),
//        MONTH(tf.dataHorarioInicio),
//        COUNT(tf.id),
//        ana.frequenciaTreino
//    )
//    FROM Aluno a
//    LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
//    JOIN AlunoTreino at ON at.alunos.id = a.id
//    JOIN TreinoExercicio te ON at.treinosExercicios.id = te.id
//    JOIN Exercicio e ON te.exercicio.id = e.id
//    JOIN TreinoFinalizado tf ON tf.alunoTreino.id = at.id
//    WHERE a.id = :alunoId
//      AND e.id = :exercicioId
//    GROUP BY
//        a.id, a.nome, e.id, e.nome, YEAR(tf.dataHorarioInicio), MONTH(tf.dataHorarioInicio), ana.frequenciaTreino
//    ORDER BY YEAR(tf.dataHorarioInicio), MONTH(tf.dataHorarioInicio)
//""")
//    List<EvolucaoTreinoCumpridoResponseDTO> buscarEvolucaoTreinosCumpridosMensal(
//            @Param("alunoId") Integer alunoId,
//            @Param("exercicioId") Integer exercicioId
//    );

//    @Query(value = """
//            SELECT\s
//            a.id AS idAluno,
//            p.nome AS nomeAluno,
//            ex.id AS idExercicio,
//            ex.nome AS nomeExercicio,
//            YEAR(st.data_horario_inicio) AS ano,
//            MONTH(st.data_horario_inicio) AS mes,
//            YEARWEEK(st.data_horario_inicio, 1) AS anoSemana,
//            ROUND(SUM(TIMESTAMPDIFF(MINUTE, st.data_horario_inicio, st.data_horario_fim)) / 60, 2) AS horasTreinadas
//        FROM sessao_treinos st
//        JOIN alunos_treinos at ON st.alunos_treinos_id = at.id
//        JOIN alunos_treinos_exercicios ate ON ate.aluno_treino_id = at.id
//        JOIN exercicios ex ON ate.exercicio_id = ex.id
//        JOIN alunos a ON at.aluno_id = a.id
//        JOIN pessoas p ON a.id = p.id
//        WHERE st.data_horario_inicio <= NOW()
//          AND ex.id = ?\s
//          AND a.id = ?\s
//          AND st.status = 'REALIZADO'
//        GROUP BY\s
//            a.id,\s
//            ex.id,\s
//            YEAR(st.data_horario_inicio),\s
//            MONTH(st.data_horario_inicio),\s
//            YEARWEEK(st.data_horario_inicio, 1)
//        ORDER BY ano, mes, anoSemana;
//        """, nativeQuery = true)
//    List<Object[]> buscarHorasAgrupadasPorAlunoExercicio(
//            @Param("alunoId") Integer alunoId,
//            @Param("exercicioId") Integer exercicioId
//    );
}
