package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoCargaDashboardResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoTreinoCumpridoResponseDTO;
import tech.vitalis.caringu.entity.SessaoTreino;

import java.util.List;

@Repository
public interface SessaoTreinoRepository extends JpaRepository<SessaoTreino, Integer> {

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioInicio, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.planoContratado.aluno.id = :alunoId
      AND FUNCTION('YEARWEEK', st.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosInicioSemana(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioFim, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.planoContratado.aluno.id = :alunoId
      AND FUNCTION('YEARWEEK', st.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosFimSemana(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioInicio, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.planoContratado.aluno.id = :alunoId
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosInicioTotal(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT FUNCTION('DATE_FORMAT', st.dataHorarioFim, '%Y-%m-%d %H:%i')
    FROM SessaoTreino st
    WHERE st.planoContratado.aluno.id = :alunoId
    ORDER BY st.dataHorarioInicio
""")
    List<String> buscarHorariosFimTotal(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO(
                    a.id,
                    pa.nome,
                    pa.urlFotoPerfil,
                    st.id,
                    st.dataHorarioInicio,
                    st.dataHorarioFim,
                    st.status
                )
                FROM PlanoContratado pc
                JOIN Plano pl
                    ON pc.plano.id = pl.id
                JOIN PersonalTrainer pt
                    ON pl.personalTrainer.id = pt.id
                JOIN Pessoa pp
                    ON pt.id = pp.id
                JOIN Aluno a
                    ON pc.aluno.id = a.id
                JOIN Pessoa pa
                    ON a.id = pa.id
                JOIN SessaoTreino st
                    ON pc.id = st.planoContratado.id
                WHERE pc.status = 'ATIVO'
                  AND NOW() BETWEEN pc.dataContratacao AND pc.dataFim
                  AND pt.id = :idPersonal
            """)
    List<SessaoAulasAgendadasResponseDTO> findAllAulasPorPersonal(@Param("idPersonal") Integer idPersonal);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoCargaDashboardResponseDTO(
        a.id,
        a.nome,
        a.peso,
        a.altura,
        st.dataHorarioInicio,
        st.dataHorarioFim,
        ee.cargaExecutada,
        ex.nome
    )
    FROM SessaoTreino st
    JOIN PlanoContratado pc ON st.planoContratado.id = pc.id
    JOIN SessaoTreinoExercicio ste ON ste.sessaoTreino.id = st.id
    JOIN TreinoExercicio te ON te.id = ste.treinoExercicio.id
    JOIN ExecucaoExercicio ee ON ee.sessaoTreinoExercicio.id = ste.id
    JOIN Exercicio ex ON te.exercicio.id = ex.id
    JOIN Aluno a ON pc.aluno.id = a.id
    WHERE ex.id = :idExercicio
      AND a.id = :idAluno
      AND st.status = 'REALIZADO'
    ORDER BY a.nome, st.dataHorarioInicio
""")
    List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(
            @Param("idExercicio") Integer idExercicio,
            @Param("idAluno") Integer idAluno
    );

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoTreinoCumpridoResponseDTO(
        a.id,
        a.nome,
        ex.id,
        ex.nome,
        YEAR(st.dataHorarioInicio),
        MONTH(st.dataHorarioInicio),
        COUNT(st.id),
        ana.frequenciaTreino
    )
    FROM SessaoTreino st
    JOIN PlanoContratado pc ON st.planoContratado.id = pc.id
    JOIN Aluno a ON a.id = pc.aluno.id
    LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
    JOIN SessaoTreinoExercicio ste ON ste.sessaoTreino.id = st.id
    JOIN TreinoExercicio te ON te.id = ste.treinoExercicio.id
    JOIN Exercicio ex ON te.exercicio.id = ex.id
    WHERE a.id = :alunoId
      AND ex.id = :exercicioId
      AND st.status = 'REALIZADO'
    GROUP BY
        a.id, a.nome, ex.id, ex.nome, YEAR(st.dataHorarioInicio), MONTH(st.dataHorarioInicio), ana.frequenciaTreino
    ORDER BY YEAR(st.dataHorarioInicio), MONTH(st.dataHorarioInicio)
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
            YEAR(st.data_horario_inicio) AS ano,
            MONTH(st.data_horario_inicio) AS mes,
            YEARWEEK(st.data_horario_inicio, 1) AS anoSemana,
            ROUND(SUM(TIMESTAMPDIFF(MINUTE, st.data_horario_inicio, st.data_horario_fim)) / 60, 2) AS horasTreinadas
        FROM sessao_treinos st
        JOIN planos_contratados pc ON st.planos_contratados_id = pc.id
        JOIN alunos a ON pc.alunos_id = a.id
        JOIN pessoas p ON a.id = p.id
        JOIN sessao_treinos_exercicios ste ON ste.sessao_treinos_id = st.id
        JOIN treinos_exercicios te ON ste.treinos_exercicios_id = te.id
        JOIN exercicios ex ON te.exercicios_id = ex.id
        WHERE st.data_horario_inicio <= NOW()
          AND ex.id = :exercicioId
          AND a.id = :alunoId
          AND st.status = 'REALIZADO'
        GROUP BY
            a.id,
            ex.id,
            YEAR(st.data_horario_inicio),
            MONTH(st.data_horario_inicio),
            YEARWEEK(st.data_horario_inicio, 1)
        ORDER BY ano, mes, anoSemana
        """, nativeQuery = true)
    List<Object[]> buscarHorasAgrupadasPorAlunoExercicio(
            @Param("alunoId") Integer alunoId,
            @Param("exercicioId") Integer exercicioId
    );
}
