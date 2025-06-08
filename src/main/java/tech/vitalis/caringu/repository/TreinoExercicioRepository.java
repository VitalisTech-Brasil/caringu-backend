package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.TreinoExercicio.ListaExercicioPorTreinoResponseDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioEditResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;

import java.util.List;

@Repository
public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Integer> {

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO(
        e.nome AS nome_exercicio,
        e.id AS exercicio_id,
        t.id AS treino_id,
        t.nome AS nomeTreino,
        te.grauDificuldade,
        te.origemTreinoExercicio
    )
    FROM TreinoExercicio te
    JOIN te.treinos t
    JOIN te.exercicio e
    WHERE t.personal.id = :personalId
""")
    List<TreinoExercicioResumoModeloCruQuerySqlDTO> buscarTreinosExerciciosPorPersonal(@Param("personalId") Integer personalId);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.ListaExercicioPorTreinoResponseDTO(
        MIN(te.id), MIN(te.exercicio.id), e.nome, t.nome
    )
    FROM TreinoExercicio te
    JOIN te.exercicio e
    JOIN te.treinos t
    WHERE te.treinos.id = :treinoId
    GROUP BY e.nome, t.nome
""")
    List<ListaExercicioPorTreinoResponseDTO> buscarExerciciosPorTreino(@Param("treinoId") Integer treinoId);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioEditResponseGetDTO(
        t.nome AS nomeTreino, t.descricao AS descricaoTreino, t.personal.id,
        
        te.id AS idTreinoExercicio, te.treinos.id, te.exercicio.id, te.carga,
        te.repeticoes, te.series, te.descanso, te.dataHoraCriacao,
        te.dataHoraModificacao, te.origemTreinoExercicio,
        t.favorito AS favoritoTreino, te.grauDificuldade,
        
        e.nome AS nomeExercicio, e.grupoMuscular, e.urlVideo,
        e.observacoes, e.favorito AS favoritoExercicio, e.origem AS origemExercicio
    )
    FROM Treino t
    JOIN TreinoExercicio te ON t.id = te.treinos.id
    JOIN Exercicio e ON e.id = te.exercicio.id
    WHERE t.personal.id = :personalId AND t.id = :treinoId
""")
    List<TreinoExercicioEditResponseGetDTO> buscarInfosEditTreinoExercicio(@Param("personalId") Integer personalId,
                                                                           @Param("treinoId") Integer treinoId);

    boolean existsByTreinosAndExercicio_Id(Treino treinos, Integer exercicioId);

    boolean existsByTreinos_IdAndExercicio_Id(Integer treinosId, Integer exercicioId);

    List<TreinoExercicio> findByTreinos_Id(Integer treinosId);

    List<TreinoExercicio> findAllByTreinos_Id(Integer treinoId);

    List<TreinoExercicio> findAllByExercicioId(Integer id);
}
