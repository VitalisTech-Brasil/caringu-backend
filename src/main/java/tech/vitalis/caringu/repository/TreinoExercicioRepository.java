package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.dtos.TreinoExercicio.ExerciciosPorTreinoResponseDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioEditResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Integer> {

    List<TreinoExercicio> findAllByExercicioId(Integer id);

    List<TreinoExercicio> findByTreinoId(Integer idTreino);

    List<TreinoExercicio> findByTreino_Id(Integer treinoId);

    boolean existsByTreino_IdAndExercicio_Id(Integer treinosId, Integer exercicioId);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO(
                    e.nome AS nome_exercicio,
                    e.id AS exercicio_id,
                    t.id AS treino_id,
                    t.nome AS nomeTreino,
                    t.grauDificuldade,
                    t.origem,
                    t.favorito
                )
                FROM TreinoExercicio te
                JOIN te.treino t
                JOIN te.exercicio e
                WHERE t.personal.id = :personalId OR t.personal.id IS NULL
            """)
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorPersonal(@Param("personalId") Integer personalId);

    @Query("""
                SELECT DISTINCT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO(
                    e.nome,
                    e.id,
                    t.id,
                    t.nome,
                    t.grauDificuldade,
                    t.origem,
                    t.favorito
                )
                FROM Aula au
                JOIN PlanoContratado pc ON au.planoContratado.id = pc.id
                JOIN AulaTreinoExercicio ate ON au.id = ate.aula.id
                JOIN TreinoExercicio te ON te.id = ate.treinoExercicio.id
                JOIN Treino t ON t.id = te.treino.id
                JOIN Exercicio e ON e.id = te.exercicio.id
                WHERE pc.aluno.id = :alunoId
            """)
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorAluno(
            @Param("alunoId") Integer alunoId
    );

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.ExerciciosPorTreinoResponseDTO(
                    MIN(te.id),
                    MIN(te.exercicio.id),
                    e.nome,
                    t.nome
                )
                FROM TreinoExercicio te
                JOIN te.exercicio e
                JOIN te.treino t
                JOIN AulaTreinoExercicio ate ON ate.treinoExercicio.id = te.id
                JOIN Aula au ON ate.aula.id = au.id
                JOIN PlanoContratado pc ON au.planoContratado.id = pc.id
                JOIN Aluno a ON a.id = pc.aluno.id
                WHERE
                    te.treino.id = :treinoId
                    AND pc.aluno.id = :alunoId
                GROUP BY e.nome, t.nome
            """)
    List<ExerciciosPorTreinoResponseDTO> buscarExerciciosPorTreino(
            @Param("treinoId") Integer treinoId,
            @Param("alunoId") Integer alunoId
    );

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioEditResponseGetDTO(
                    t.nome AS nomeTreino, t.descricao AS descricaoTreino, t.personal.id,
            
                    te.id AS idTreinoExercicio, te.treino.id, te.exercicio.id, te.carga,
                    te.repeticoes, te.series, te.descanso,
                    te.dataModificacao, t.origem,
                    t.favorito AS favoritoTreino, t.grauDificuldade,
            
                    e.nome AS nomeExercicio, e.grupoMuscular, e.urlVideo,
                    e.observacoes, e.favorito AS favoritoExercicio, e.origem AS origemExercicio
                )
                FROM Treino t
                JOIN TreinoExercicio te ON t.id = te.treino.id
                JOIN Exercicio e ON e.id = te.exercicio.id
                WHERE t.personal.id = :idPersonal AND t.id = :idTreino
            """)
    List<TreinoExercicioEditResponseGetDTO> buscarInfosEditTreinoExercicio(@Param("idPersonal") Integer idPersonal,
                                                                           @Param("idTreino") Integer idTreino);
}
