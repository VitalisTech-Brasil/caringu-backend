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
                    ana.protesesDescricao, ana.doencaMetabolica, ana.doencaMetabolicaDescricao, ana.deficiencia, ana.deficienciaDescricao
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

    @Query("""
            SELECT e.id as exercicioId,
                   e.nome as nomeExercicio,
                   MIN(ee.cargaExecutada) as cargaAntiga,
                   MAX(ee.cargaExecutada) as cargaAtual
            FROM PlanoContratado pc
            INNER JOIN Aula a ON a.planoContratado.id = pc.id
            INNER JOIN AulaTreinoExercicio ate ON ate.aula.id = a.id
            INNER JOIN ExecucaoExercicio ee ON ee.aulaTreinoExercicio.id = ate.id
            INNER JOIN TreinoExercicio te ON te.id = ate.treinoExercicio.id
            INNER JOIN Exercicio e ON e.id = te.exercicio.id
            WHERE pc.aluno.id = :alunoId
            AND a.status = 'REALIZADO'
            AND YEAR(a.dataHorarioInicio) = YEAR(CURRENT_DATE)
            AND MONTH(a.dataHorarioInicio) = MONTH(CURRENT_DATE)
            AND ee.cargaExecutada IS NOT NULL
            GROUP BY e.id, e.nome
            HAVING MAX(ee.cargaExecutada) > MIN(ee.cargaExecutada)
            ORDER BY (MAX(ee.cargaExecutada) - MIN(ee.cargaExecutada)) DESC
            """)
    List<Object[]> findMaiorEvolucaoExercicioPorAlunoMesAtual(@Param("alunoId") Long alunoId);

    // NÃO ESTAVA SENDO UTILIZADO -> Resolvi comentar
//    @Query("""
//    SELECT new tech.vitalis.caringu.dtos.Aluno.AlunoDetalhadoResponseDTO(
//        a.id, a.peso, a.altura, a.nome, a.email, a.celular, a.urlFotoPerfil, a.nivelExperiencia, a.nivelAtividade,
//        pl.nome, pl.periodo, pl.quantidadeAulas, pc.dataFim,
//        at.id,
//        COUNT(DISTINCT tfSemana.id),
//        COUNT(DISTINCT tfTotal.id),
//        ana.id, ana.objetivoTreino, ana.lesao, ana.lesaoDescricao, ana.frequenciaTreino,
//        ana.experiencia, ana.experienciaDescricao, ana.desconforto, ana.desconfortoDescricao,
//        ana.fumante, ana.proteses, ana.protesesDescricao,
//        ana.doencaMetabolica, ana.doencaMetabolicaDescricao,
//        ana.deficiencia, ana.deficienciaDescricao
//    )
//    FROM PlanoContratado pc
//    JOIN pc.plano pl
//    JOIN pl.personalTrainer pt
//    JOIN pc.aluno a
//    LEFT JOIN Anamnese ana ON ana.aluno.id = a.id
//    LEFT JOIN AlunoTreino at ON at.alunos.id = a.id
//    LEFT JOIN TreinoFinalizado tfSemana
//        ON tfSemana.alunoTreino.id = at.id
//        AND FUNCTION('YEARWEEK', tfSemana.dataHorarioInicio, 1) = FUNCTION('YEARWEEK', CURRENT_DATE, 1)
//    LEFT JOIN TreinoFinalizado tfTotal ON tfTotal.alunoTreino.id = at.id
//    WHERE pt.id = :personalId
//      AND pc.status = 'ATIVO'
//      AND pc.dataContratacao = (
//        SELECT MAX(p2.dataContratacao)
//        FROM PlanoContratado p2
//        WHERE p2.aluno.id = pc.aluno.id AND p2.status = 'ATIVO'
//    )
//    GROUP BY a.id, a.nome, a.celular, a.urlFotoPerfil, a.nivelExperiencia,
//             pl.nome, pl.periodo, pl.quantidadeAulas, pc.dataFim,
//             at.id, ana.id
//""")
//    Page<AlunoDetalhadoResponseDTO> buscarDetalhesPorPersonal(@Param("personalId") Integer personalId, Pageable pageable);

}
