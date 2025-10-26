package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.TreinoDetalhadoRepositoryDTO;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;

import java.util.List;

public interface AulaTreinoExercicioRepository extends JpaRepository<AulaTreinoExercicio, Integer> {

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.AulaTreinoExercicio.TreinoDetalhadoRepositoryDTO(
                    t.id,
                    t.nome,
                    te.id,
                    a.id,
                    ee.id,
                    e.nome,
                    ate.descanso,
                    CONCAT(CAST(ate.series AS string), ' x ', CAST(ate.repeticoes AS string)),
                    CONCAT(CAST(ate.carga AS string), ' kg'),
                    e.grupoMuscular,
                    COALESCE(ate.observacoesPersonalizadas, ''),
                    COALESCE(e.urlVideo, ''),
                    CASE WHEN a.status = 'REALIZADO' THEN true ELSE false END
                )
                FROM AulaTreinoExercicio ate
                JOIN ate.aula a
                JOIN a.planoContratado pc
                JOIN TreinoExercicio te ON te.id = ate.treinoExercicio.id
                JOIN Treino t ON t.id = te.treino.id
                JOIN Exercicio e ON e.id = te.exercicio.id
                LEFT JOIN ExecucaoExercicio ee ON ee.aulaTreinoExercicio.id = ate.id
                WHERE a.id = :idAula
                AND pc.aluno.id = :idAluno
            """)
    List<TreinoDetalhadoRepositoryDTO> listarAulasComTreinosExercicios(
            @Param("idAula") Integer idAula,
            @Param("idAluno") Integer idAluno
    );

    @Query("""
                SELECT
                    COALESCE(MAX(ate.ordem), 0)
                FROM AulaTreinoExercicio ate
                WHERE ate.aula.id = :idAula
            """)
    Integer findMaxOrdemByAulaId(@Param("idAula") Integer idAula);

    List<AulaTreinoExercicio> findByAulaId(Integer aulaId);
}
