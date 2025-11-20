package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Aluno.AlunoDetalhadoResponseDTO;
import tech.vitalis.caringu.entity.Aluno;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    @Query("""
                SELECT new tech.vitalis.caringu.dtos.Aluno.AlunoDetalhadoResponseDTO(
                    a.id, a.peso, a.altura, p.nome, p.email, p.celular, p.urlFotoPerfil, a.nivelExperiencia,
                    a.nivelAtividade, pl.nome, pl.periodo, pl.quantidadeAulas, pc.dataFim,
                    pc.id, au.id,
                    SUM(CASE WHEN au.status = 'REALIZADO' AND au.dataHorarioInicio BETWEEN :startOfWeek AND :endOfWeek
                                     THEN 1 ELSE 0 END),
                    SUM(CASE WHEN au.status = 'REALIZADO' THEN 1 ELSE 0 END),
                    ana.id, ana.objetivoTreino, ana.lesao, ana.lesaoDescricao, ana.frequenciaTreino, ana.experiencia,
                    ana.experienciaDescricao, ana.desconforto, ana.desconfortoDescricao, ana.fumante, ana.proteses,
                    ana.protesesDescricao, ana.doencaMetabolica, ana.doencaMetabolicaDescricao, ana.deficiencia, ana.deficienciaDescricao,
                    p.genero, p.dataNascimento
                )
                FROM PlanoContratado pc
                JOIN pc.plano pl
                JOIN pl.personalTrainer pt
                JOIN pc.aluno a
                JOIN Pessoa p ON p.id = a.id
                LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
                LEFT JOIN Aula au ON au.planoContratado.id = pc.id
                WHERE pt.id = :idPersonal
                  AND pc.status = 'ATIVO'
                  AND pc.dataContratacao = (
                    SELECT MAX(p2.dataContratacao)
                    FROM PlanoContratado p2
                    WHERE p2.aluno.id = pc.aluno.id
                      AND p2.status = 'ATIVO'
                  )
                  GROUP BY a.id, p.nome, p.celular, p.urlFotoPerfil, a.nivelExperiencia, a.nivelAtividade,
                               pl.nome, pl.periodo, pl.quantidadeAulas, pc.dataFim,
                               pc.id, au.id, ana.id, ana.objetivoTreino, ana.lesao, ana.lesaoDescricao,
                               ana.frequenciaTreino, ana.experiencia, ana.experienciaDescricao,
                               ana.desconforto, ana.desconfortoDescricao, ana.fumante, ana.proteses,
                               ana.protesesDescricao, ana.doencaMetabolica, ana.doencaMetabolicaDescricao,
                               ana.deficiencia, ana.deficienciaDescricao
            """)
    List<AlunoDetalhadoResponseDTO> buscarDetalhesPorPersonal(@Param("idPersonal") Integer idPersonal,
                                                              @Param("startOfWeek") LocalDateTime startOfWeek,
                                                              @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("""
            SELECT
            	CASE
            			WHEN COUNT(*) > 0
            			THEN false
            		ELSE true
                END AS alunoJaContratouPlano
            FROM Pessoa p
            JOIN Aluno a ON p.id = a.id
            LEFT JOIN PlanoContratado pc ON pc.aluno.id = a.id
            LEFT JOIN Plano pl ON pc.plano.id = pl.id
            WHERE
                pc.id IS NULL
                    AND
                a.id = :idAluno
            """)
    Boolean validarContratacaoPlanoAluno(@Param("idAluno") Integer idAluno);

    @Query("""
            SELECT pc.aluno.id as alunosId,
                   t.id as treinoId, 
                   t.nome as treinoNome, 
                   COUNT(DISTINCT a.id) as qtdVezesRealizado
            FROM PlanoContratado pc
            JOIN Aula a ON pc.id = a.planoContratado.id
            JOIN AulaTreinoExercicio ate ON a.id = ate.aula.id
            JOIN TreinoExercicio te ON te.id = ate.treinoExercicio.id
            JOIN Treino t ON t.id = te.treino.id
            WHERE pc.aluno.id = :alunosId
            AND YEAR(a.dataHorarioInicio) = YEAR(CURRENT_DATE)
            AND MONTH(a.dataHorarioInicio) = MONTH(CURRENT_DATE)
            GROUP BY pc.aluno.id, t.id, t.nome
            ORDER BY qtdVezesRealizado DESC
            """)
    List<Object[]> findTopTreinosByAlunosIdCurrentMonth(@Param("alunosId") Long alunosId);

    @Query("""
            SELECT pc.aluno.id as alunoId,
                   COUNT(a.id) as totalAulas,
                   COUNT(CASE WHEN a.status = 'REALIZADO' THEN 1 END) as aulasRealizadas
            FROM PlanoContratado pc
            LEFT JOIN Aula a ON a.planoContratado.id = pc.id
            WHERE pc.aluno.id = :alunoId
            GROUP BY pc.aluno.id
            """)
    List<Object[]> findProgressoAulasByAlunoId(@Param("alunoId") Long alunoId);

    @Query(value = """
            SELECT
                e.id AS exercicioId,
                e.nome AS nomeExercicio,
                MIN(ee.carga_executada) AS cargaAntiga,
                MAX(ee.carga_executada) AS cargaAtual,
                (MAX(ee.carga_executada) - MIN(ee.carga_executada)) AS evolucao
            FROM planos_contratados pc
            JOIN alunos al ON al.id = pc.alunos_id
            JOIN aulas a ON a.planos_contratados_id = pc.id
            JOIN aulas_treinos_exercicios ate ON ate.aulas_id = a.id
            JOIN execucoes_exercicios ee ON ee.aulas_treinos_exercicios_id = ate.id
            JOIN treinos_exercicios te ON te.id = ate.treinos_exercicios_id
            JOIN exercicios e ON e.id = te.exercicios_id
            WHERE al.id = :alunoId
              AND a.status = 'REALIZADO'
              AND a.data_horario_inicio BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY) AND CURRENT_DATE()
              AND ee.carga_executada > 0
              AND ee.finalizado = TRUE
            GROUP BY e.id, e.nome
            HAVING COUNT(ee.id) > 1
               AND MAX(ee.carga_executada) > MIN(ee.carga_executada)
            ORDER BY evolucao DESC
            LIMIT 1;
            """, nativeQuery = true)
    List<Object[]> findMaiorEvolucaoExercicioPorAlunoMesAtual(@Param("alunoId") Long alunoId);

    @Query(value = """
            SELECT 
                CURRENT_DATE() as data_atual,
                YEAR(CURRENT_DATE()) as ano_atual,
                MONTH(CURRENT_DATE()) as mes_atual,
                COUNT(ee.id) as total_execucoes,
                COUNT(CASE WHEN ee.carga_executada > 0 THEN 1 END) as execucoes_com_carga,
                GROUP_CONCAT(DISTINCT DATE(a.data_horario_inicio)) as datas_aulas,
                GROUP_CONCAT(DISTINCT ee.carga_executada ORDER BY ee.carga_executada) as cargas_executadas
            FROM plano_contratado pc
            JOIN aluno al ON al.id = pc.aluno_id
            JOIN aula a ON a.plano_contratado_id = pc.id
            JOIN aula_treino_exercicio ate ON ate.aula_id = a.id
            JOIN execucao_exercicio ee ON ee.aula_treino_exercicio_id = ate.id
            WHERE al.id = :alunoId
            AND a.status = 'REALIZADO'
            """, nativeQuery = true)
    List<Object[]> diagnosticoDataECargasPorAluno(@Param("alunoId") Long alunoId);
}
