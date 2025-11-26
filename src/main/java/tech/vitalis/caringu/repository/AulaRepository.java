package tech.vitalis.caringu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Aula.AulasPendentesNotificacaoDTO;
import tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulaRascunhoResponseGetDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulasAlunoRequestDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulasAgendadasResponseDTO;
import tech.vitalis.caringu.dtos.Aula.TotalAulasAgendamentoResponseGetDTO;
import tech.vitalis.caringu.dtos.Feedback.FeedbackCountDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoCargaDashboardResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoTreinoCumpridoResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {

    @Query("""
                SELECT FUNCTION('DATE_FORMAT', au.dataHorarioInicio, '%Y-%m-%d %H:%i')
                FROM Aula au
                WHERE au.planoContratado.aluno.id = :alunoId
                  AND FUNCTION('YEARWEEK', au.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
                ORDER BY au.dataHorarioInicio
            """)
    List<String> buscarHorariosInicioSemana(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT FUNCTION('DATE_FORMAT', au.dataHorarioFim, '%Y-%m-%d %H:%i')
                FROM Aula au
                WHERE au.planoContratado.aluno.id = :alunoId
                  AND FUNCTION('YEARWEEK', au.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
                ORDER BY au.dataHorarioInicio
            """)
    List<String> buscarHorariosFimSemana(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT FUNCTION('DATE_FORMAT', au.dataHorarioInicio, '%Y-%m-%d %H:%i')
                FROM Aula au
                WHERE au.planoContratado.aluno.id = :alunoId
                ORDER BY au.dataHorarioInicio
            """)
    List<String> buscarHorariosInicioTotal(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT FUNCTION('DATE_FORMAT', au.dataHorarioFim, '%Y-%m-%d %H:%i')
                FROM Aula au
                WHERE au.planoContratado.aluno.id = :alunoId
                ORDER BY au.dataHorarioInicio
            """)
    List<String> buscarHorariosFimTotal(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT DISTINCT new tech.vitalis.caringu.dtos.Aula.Response.AulasAgendadasResponseDTO(
                    a.id,
                    pa.nome,
                    pa.email,
                    pa.urlFotoPerfil,
                    t.id,
                    t.nome,
                    au.id,
                    au.dataHorarioInicio,
                    au.dataHorarioFim,
                    au.status
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
                JOIN Aula au
                    ON pc.id = au.planoContratado.id
                JOIN AulaTreinoExercicio ate
                   ON ate.aula.id = au.id
               JOIN TreinoExercicio te
                   ON ate.treinoExercicio.id = te.id
               JOIN Treino t
                   ON te.treino.id = t.id
                WHERE pc.status = 'ATIVO'
                  AND NOW() BETWEEN pc.dataContratacao AND pc.dataFim
                  AND pt.id = :idPersonal
                  AND au.id = :idAula
            """)
    List<AulasAgendadasResponseDTO> findAllInfoAulaPorPersonal(@Param("idPersonal") Integer idPersonal, @Param("idAula") Integer idAula);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO(
                    a.id,
                    pa.nome,
                    pa.urlFotoPerfil,
                    pt.id,
                    pt.nome,
                    pt.urlFotoPerfil,
                    au.id,
                    au.dataHorarioInicio,
                    au.dataHorarioFim,
                    au.status
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
                JOIN Aula au
                    ON pc.id = au.planoContratado.id
                WHERE pc.status = 'ATIVO'
                  AND NOW() BETWEEN pc.dataContratacao AND pc.dataFim
                  AND pt.id = :idPersonal
            """)
    List<SessaoAulasAgendadasResponseDTO> findAllAulasPorPersonal(@Param("idPersonal") Integer idPersonal);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO(
                    a.id,
                    a.nome,
                    a.urlFotoPerfil,
                    pt.id,
                    pt.nome,
                    pt.urlFotoPerfil,
                    au.id,
                    au.dataHorarioInicio,
                    au.dataHorarioFim,
                    au.status
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
                JOIN Aula au
                    ON pc.id = au.planoContratado.id
                WHERE pc.status = 'ATIVO'
                  AND NOW() BETWEEN pc.dataContratacao AND pc.dataFim
                  AND au.status IN (
                              tech.vitalis.caringu.enums.Aula.AulaStatusEnum.AGENDADO,
                              tech.vitalis.caringu.enums.Aula.AulaStatusEnum.REAGENDADO
                                  )
                  AND a.id = :idAluno
            """)
    List<SessaoAulasAgendadasResponseDTO> findAllAulasPorAluno(@Param("idAluno") Integer idAluno);

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
                FROM Aula st
                JOIN PlanoContratado pc ON st.planoContratado.id = pc.id
                JOIN AulaTreinoExercicio ate ON ate.aula.id = st.id
                JOIN TreinoExercicio te ON te.id = ate.treinoExercicio.id
                JOIN ExecucaoExercicio ee ON ee.aulaTreinoExercicio.id = ate.id
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
                    YEAR(au.dataHorarioInicio),
                    MONTH(au.dataHorarioInicio),
                    COUNT(au.id),
                    ana.frequenciaTreino
                )
                FROM Aula au
                JOIN PlanoContratado pc ON au.planoContratado.id = pc.id
                JOIN Aluno a ON a.id = pc.aluno.id
                LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
                JOIN AulaTreinoExercicio ate ON ate.aula.id = au.id
                JOIN TreinoExercicio te ON te.id = ate.treinoExercicio.id
                JOIN Exercicio ex ON te.exercicio.id = ex.id
                WHERE a.id = :alunoId
                  AND ex.id = :exercicioId
                  AND au.status = 'REALIZADO'
                GROUP BY
                    a.id, a.nome, ex.id, ex.nome, YEAR(au.dataHorarioInicio), MONTH(au.dataHorarioInicio), ana.frequenciaTreino
                ORDER BY YEAR(au.dataHorarioInicio), MONTH(au.dataHorarioInicio)
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
                YEAR(au.data_horario_inicio) AS ano,
                MONTH(au.data_horario_inicio) AS mes,
                YEARWEEK(au.data_horario_inicio, 1) AS anoSemana,
                ROUND(SUM(TIMESTAMPDIFF(MINUTE, au.data_horario_inicio, au.data_horario_fim)) / 60, 2) AS horasTreinadas
            FROM aulas au
            JOIN planos_contratados pc ON au.planos_contratados_id = pc.id
            JOIN alunos a ON pc.alunos_id = a.id
            JOIN pessoas p ON a.id = p.id
            JOIN aulas_treinos_exercicios ste ON ste.aulas_id = au.id
            JOIN treinos_exercicios te ON ste.treinos_exercicios_id = te.id
            JOIN exercicios ex ON te.exercicios_id = ex.id
            WHERE au.data_horario_inicio <= NOW()
              AND ex.id = :exercicioId
              AND a.id = :alunoId
              AND au.status = 'REALIZADO'
            GROUP BY
                a.id,
                ex.id,
                YEAR(au.data_horario_inicio),
                MONTH(au.data_horario_inicio),
                YEARWEEK(au.data_horario_inicio, 1)
            ORDER BY ano, mes, anoSemana
            """, nativeQuery = true)
    List<Object[]> buscarHorasAgrupadasPorAlunoExercicio(
            @Param("alunoId") Integer alunoId,
            @Param("exercicioId") Integer exercicioId
    );

    @Query("""
            SELECT new tech.vitalis.caringu.dtos.Aula.TotalAulasAgendamentoResponseGetDTO(
                a.id,
                a.nome,
                pl.quantidadeAulas,
                SUM(CASE WHEN au.status IN ('AGENDADO', 'REALIZADO', 'REAGENDADO') THEN 1 ELSE 0 END),
                SUM(CASE WHEN au.status = 'RASCUNHO' THEN 1 ELSE 0 END),
                CAST(pl.quantidadeAulas - SUM(CASE WHEN au.status IN ('AGENDADO', 'REALIZADO', 'REAGENDADO') THEN 1 ELSE 0 END) AS long)
            )
            FROM Aluno a
            JOIN PlanoContratado pc ON a.id = pc.aluno.id
            JOIN Plano pl ON pl.id = pc.plano.id
            LEFT JOIN Aula au
                ON au.planoContratado.id = pc.id
                AND au.status IN ('AGENDADO', 'REALIZADO', 'REAGENDADO', 'RASCUNHO')
            JOIN Anamnese ana ON ana.aluno.id = a.id
            WHERE pc.aluno.id = :idAluno
                AND pc.status = 'ATIVO'
            GROUP BY a.id, pl.quantidadeAulas, pl.nome, a.nivelAtividade, ana.objetivoTreino, a.celular, a.nome
            """)
    TotalAulasAgendamentoResponseGetDTO buscarDisponibilidadeDeAulas(@Param("idAluno") Integer idAluno);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulaRascunhoResponseGetDTO(
                    au.id,
                    au.dataHorarioInicio,
                    au.dataHorarioFim,
                    au.status,
                    pc.id
                )
                FROM Aula au
                JOIN au.planoContratado pc
                WHERE pc.aluno.id = :idAluno
                  AND pc.status = 'ATIVO'
                  AND au.status = 'RASCUNHO'
                ORDER BY au.dataHorarioInicio
            """)
    List<AulaRascunhoResponseGetDTO> buscarAulasRascunho(@Param("idAluno") Integer idAluno);

    // Retorna qualquer aula do plano que sobreponha o período informado
    @Query("""
                SELECT a
                FROM Aula a
                WHERE a.planoContratado.id = :planoId
                  AND a.status IN ('RASCUNHO','AGENDADO','REAGENDADO')
                  AND (
                        (a.dataHorarioInicio <= :fim AND a.dataHorarioFim >= :inicio)
                      )
            """)
    List<Aula> findAulasNoPeriodo(
            @Param("planoId") Integer planoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    Optional<Aula> findByPlanoContratadoIdAndDataHorarioInicioAndDataHorarioFimAndStatus(
            Integer idPlanoContratado,
            LocalDateTime dataHorarioInicio,
            LocalDateTime dataHorarioFim,
            AulaStatusEnum aulaStatus
    );

    @Query(value = """
    SELECT DISTINCT new tech.vitalis.caringu.dtos.Aula.Request.AulasAlunoRequestDTO(
        a.id,
        a.dataHorarioInicio,
        a.dataHorarioFim,
        pt.nome,
        t.id,
        t.nome
    )
    FROM Aula a
    JOIN a.planoContratado pc
    JOIN pc.aluno al
    JOIN pc.plano p
    JOIN p.personalTrainer pt
    JOIN AulaTreinoExercicio ate ON ate.aula.id = a.id
    JOIN ate.treinoExercicio te
    JOIN te.treino t
    WHERE al.id = :idAluno
      AND pc.status = 'ATIVO'
      AND (
          :data IS NULL
          OR (a.dataHorarioInicio >= :dataInicio AND a.dataHorarioInicio < :dataFim)
      )
    ORDER BY a.dataHorarioInicio DESC
    """,
            countQuery = """
    SELECT COUNT(DISTINCT a.id)
    FROM Aula a
    JOIN a.planoContratado pc
    JOIN pc.aluno al
    WHERE al.id = :idAluno
      AND pc.status = 'ATIVO'
      AND (
          :data IS NULL
          OR (a.dataHorarioInicio >= :dataInicio AND a.dataHorarioInicio < :dataFim)
      )
    """)
    Page<AulasAlunoRequestDTO> listarAulasPorAlunoComPlano(
            @Param("idAluno") Integer idAluno,
            @Param("data") LocalDate data,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Feedback.FeedbackCountDTO(
        a.id,
        CAST(COUNT(f.id) AS int)
    )
    FROM Aula a
    LEFT JOIN Feedback f ON f.aula.id = a.id
    WHERE a.id IN :aulaIds
    GROUP BY a.id
    """)
    List<FeedbackCountDTO> buscarQuantidadeFeedbacksPorAulas(@Param("aulaIds") List<Integer> aulaIds);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Aula.AulasPendentesNotificacaoDTO(
        a.id,
        pa.nome,
        pt.id,
        pp.nome,
        pc.id,
        CAST(pl.quantidadeAulas AS long),
        CAST((SELECT COUNT(au2) FROM Aula au2 
              WHERE au2.planoContratado.id = pc.id 
              AND au2.status IN ('AGENDADO', 'REALIZADO', 'REAGENDADO')) AS long)
    )
    FROM Aluno a
    JOIN Pessoa pa ON a.id = pa.id
    JOIN PlanoContratado pc ON a.id = pc.aluno.id
    JOIN Plano pl ON pl.id = pc.plano.id
    JOIN PersonalTrainer pt ON pl.personalTrainer.id = pt.id
    JOIN Pessoa pp ON pt.id = pp.id
    WHERE pc.status = 'ATIVO'
      AND CURRENT_TIMESTAMP BETWEEN pc.dataContratacao AND pc.dataFim
    ORDER BY pl.quantidadeAulas DESC
""")
    List<AulasPendentesNotificacaoDTO> buscarTodosPlanosAtivos();
}
