package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AcompanhamentoAulaCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoModeloCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.TreinoDetalhadoRepositoryDTO;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;

import java.util.List;
import java.util.Optional;

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
                    ate.id,
                    COALESCE(ate.observacoesPersonalizadas, ''),
                    COALESCE(e.urlVideo, ''),
                    CASE WHEN a.status = 'REALIZADO' THEN true ELSE false END,
                    ee.finalizado
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
                SELECT new tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoModeloCruDTO(
                    a.planoContratado.aluno.id,
                    a.planoContratado.aluno.nome,
                    ate.id,
                    ate.aula.id,
                    a.dataHorarioInicio,
                    a.dataHorarioFim,
                    t.id,
                    t.nome,
                    e.id,
                    e.nome,
                    p.id,
                    p.nome,
                    p.urlFotoPerfil
                )
                FROM AulaTreinoExercicio ate
                JOIN ate.aula a
                JOIN ate.treinoExercicio te
                JOIN te.treino t
                JOIN te.exercicio e
                JOIN t.personal p
                WHERE a.planoContratado.aluno.id = :idAluno
                  AND a.dataHorarioInicio > CURRENT_TIMESTAMP
                  AND a.status IN (
                              tech.vitalis.caringu.enums.Aula.AulaStatusEnum.AGENDADO,
                              tech.vitalis.caringu.enums.Aula.AulaStatusEnum.REAGENDADO
                                  )
                ORDER BY a.dataHorarioInicio ASC
            """)
    List<AulaComTreinoModeloCruDTO> listarProximasAulas(@Param("idAluno") Integer idAluno);

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.AulaTreinoExercicio.AcompanhamentoAulaCruDTO(
                    al.id,
                    a.id,
                    a.status,
                    a.dataHorarioInicio,
                    a.dataHorarioFim,
                    t.id,
                    t.nome,
                    ee.id,
                    e.nome,
                    ee.cargaExecutada,
                    ee.repeticoesExecutadas,
                    ee.seriesExecutadas,
                    ee.descansoExecutado,
                    ate.id,
                    ate.observacoesPersonalizadas,
                    e.urlVideo,
                    te.exercicio.grupoMuscular,
                    ee.finalizado
                )
                FROM AulaTreinoExercicio ate
                JOIN ate.treinoExercicio te
                JOIN te.treino t
                JOIN te.exercicio e
                JOIN ate.aula a
                JOIN ExecucaoExercicio ee ON ee.aulaTreinoExercicio.id = ate.id
                JOIN PlanoContratado pc ON a.planoContratado.id = pc.id
                JOIN pc.aluno as al
                WHERE a.id = :idAula AND a.status != tech.vitalis.caringu.enums.Aula.AulaStatusEnum.RASCUNHO
            """)
    List<AcompanhamentoAulaCruDTO> listarAcompanharDaAula(@Param("idAula") Integer idAula);

    @Query("""
                SELECT
                    COALESCE(MAX(ate.ordem), 0)
                FROM AulaTreinoExercicio ate
                WHERE ate.aula.id = :idAula
            """)
    Integer findMaxOrdemByAulaId(@Param("idAula") Integer idAula);

    List<AulaTreinoExercicio> findByAulaId(Integer aulaId);
}
