package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.dtos.AlunosTreinoExercicio.ExerciciosPorTreinoResponseDTO;
import tech.vitalis.caringu.dtos.AlunosTreinoExercicio.TreinoExercicioEditResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.AlunoTreinoExercicio;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

public interface AlunoTreinoExercicioRepository extends JpaRepository<AlunoTreinoExercicio, Integer> {

    List<AlunoTreinoExercicio> findAllByExercicioId(Integer id);
    List<AlunoTreinoExercicio> findByTreinoId(Integer idTreino);

    List<AlunoTreinoExercicio> findByTreino_Id(Integer treinoId);
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
                FROM AlunoTreinoExercicio ate
                JOIN ate.treino t
                JOIN ate.exercicio e
                WHERE ate.icModel = true AND (t.personal.id = :personalId OR t.personal.id IS NULL)
            """)
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorPersonal(@Param("personalId") Integer personalId);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO(
                	e.nome,
                	e.id,
                	t.id,
                	t.nome,
                	t.grauDificuldade,
                	t.origem,
                	t.favorito
                )
                FROM AlunoTreinoExercicio ate
                join Treino t ON ate.treino.id = t.id
                join Exercicio e ON ate.exercicio.id = e.id
                join AlunoTreino at ON ate.alunoTreino.id = at.id
                where at.alunos.id = 6
            """)
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorAluno(@Param("alunoId") Integer alunoId);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.AlunosTreinoExercicio.ExerciciosPorTreinoResponseDTO(
        ate.id,
        e.id,
        e.nome,
        t.nome
    )
    FROM AlunoTreinoExercicio ate
    JOIN ate.exercicio e
    JOIN ate.treino t
    JOIN ate.alunoTreino at
    JOIN at.alunos a
    WHERE a.id = :alunoId
      AND t.id = :treinoId
""")
    List<ExerciciosPorTreinoResponseDTO> buscarExerciciosPorTreino(
            @Param("treinoId") Integer treinoId,
            @Param("alunoId") Integer alunoId
    );

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.AlunosTreinoExercicio.TreinoExercicioEditResponseGetDTO(
                    t.nome AS nomeTreino, t.descricao AS descricaoTreino, t.personal.id,
            
                    ate.id AS idTreinoExercicio, ate.treino.id, ate.exercicio.id, ate.carga,
                    ate.repeticoes, ate.series, ate.descanso,
                    ate.dataModificacao, t.origem,
                    t.favorito AS favoritoTreino, t.grauDificuldade,
            
                    e.nome AS nomeExercicio, e.grupoMuscular, e.urlVideo,
                    e.observacoes, e.favorito AS favoritoExercicio, e.origem AS origemExercicio
                )
                FROM Treino t
                JOIN AlunoTreinoExercicio ate ON t.id = ate.treino.id
                JOIN Exercicio e ON e.id = ate.exercicio.id
                WHERE t.personal.id = :idPersonal AND t.id = :idTreino
            """)
    List<TreinoExercicioEditResponseGetDTO> buscarInfosEditTreinoExercicio(@Param("idPersonal") Integer idPersonal,
                                                                           @Param("idTreino") Integer idTreino);
}
