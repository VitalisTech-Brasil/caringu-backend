package tech.vitalis.caringu.infrastructure.persistence.planoContratado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.core.domain.entity.PlanoContratado;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPagamentoPendenteResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPagamentoPendenteResponse;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPendenteRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoContratadoRepository  extends JpaRepository<PlanoContratadoEntity, Integer> {

    Optional<PlanoContratadoEntity> findFirstByAlunoIdAndStatus(Integer alunoId, StatusEnum status);
    List<PlanoContratadoEntity> findByDataFimBetweenAndStatus(LocalDate hoje, LocalDate daquiDuasSemans, StatusEnum status);
    Optional<PlanoContratadoEntity> findByAlunoIdAndStatus(Integer alunoId, StatusEnum statusEnum);

    @Query("SELECT COUNT(DISTINCT pc.aluno.id) " +
            "FROM PlanoContratado pc " +
            "WHERE pc.status = 'ATIVO' " +
            "AND pc.plano.personalTrainer.id = :personalId")
    Integer countAlunosAtivosByPersonalId(@Param("personalId") Integer personalId);

    @Query("""
            SELECT new tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO(
                    pc.id,
                    a.nome,
                    a.celular,
                    p.nome,
                    p.periodo,
                    p.quantidadeAulas,
                    p.valorAulas,
                    pc.status
                )
                FROM PlanoContratado pc
                JOIN Plano p
                    ON pc.plano.id = p.id
                JOIN Aluno a
                    ON pc.aluno.id = a.id
                WHERE pc.status = "EM_PROCESSO" AND p.personalTrainer.id = :personalId
            """)
    List<PlanoContratadoPendenteRequestDTO> listarSolicitacoesPendentes(@Param("personalId") Integer personalId);

    @Query("""
        SELECT pc
            FROM PlanoContratadoEntity pc 
            JOIN pc.plano p
            JOIN pc.aluno a
            WHERE pc.status = 'EM_PROCESSO' AND p.personalTrainer.id = :personalId
        """)
    List<PlanoContratadoEntity> listSolicitacoesPendentes(@Param("personalId") Integer personalId);

    @Query("""
            SELECT new tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPagamentoPendenteResponseDTO(
                pc.id,
                pc.plano.id,
                pc.aluno,
                pc.status,
                pc.dataContratacao,
                pc.dataFim
            )
            FROM PlanoContratado pc
            WHERE pc.aluno.id = :alunoId
            AND pc.status IN ("PENDENTE", "EM_PROCESSO", "ATIVO")
            """)
    List<PlanoContratadoPagamentoPendenteResponseDTO> buscarPorAlunoIdStatus(Integer alunoId);

    @Query("SELECT pc FROM PlanoContratadoEntity pc WHERE pc.aluno.id = :alunoId AND pc.status IN ('PENDENTE', 'EM_PROCESSO', 'ATIVO')")
    List<PlanoContratadoEntity> findPorAlunoIdStatus(@Param("alunoId") Integer alunoId);

    @Query("""
                SELECT DISTINCT new tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto(
                    pt,
                    pePersonal.nome,
                    a,
                    peAluno.nome,
                    p.id,
                    p.nome,
                    pc,
                    pc.status,
                    pc.dataContratacao,
                    pc.dataFim,
                    n.visualizada
                )
                FROM Aluno a
                JOIN PlanoContratado pc ON pc.aluno.id = a.id
                JOIN pc.plano p
                JOIN p.personalTrainer pt
                JOIN Pessoa pePersonal ON pePersonal.id = pt.id
                JOIN Treino t ON pt.id = t.personal.id
                JOIN TreinoExercicio te ON te.treino.id = t.id
                JOIN Pessoa peAluno ON peAluno.id = a.id
                LEFT JOIN Notificacoes n ON n.pessoa.id = peAluno.id
                WHERE pc.dataFim IS NOT NULL
                  AND pc.dataFim BETWEEN CURRENT_DATE AND :duasSemanasDepois
                  AND pc.status NOT IN ('INATIVO', 'PENDENTE')
                  AND pt.id = :personalTrainerId
            """)
    List<NotificacaoPlanoVencimentoDto> findNotificacoesPlanoVencimentoPorPersonal(
            @Param("duasSemanasDepois") LocalDate duasSemanasDepois,
            @Param("personalTrainerId") Integer personalTrainerId
    );

    @Query("""
                SELECT DISTINCT new tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto(
                    pt,
                    pePersonal.nome,
                    a,
                    peAluno.nome,
                    p.id,
                    p.nome,
                    pc,
                    pc.status,
                    pc.dataContratacao,
                    pc.dataFim,
                    n.visualizada
                )
                FROM Aluno a
                JOIN PlanoContratado pc ON pc.aluno.id = a.id
                JOIN pc.plano p
                JOIN p.personalTrainer pt
                JOIN Pessoa pePersonal ON pePersonal.id = pt.id
                JOIN Treino t ON pt.id = t.personal.id
                JOIN TreinoExercicio te ON te.treino.id = t.id
                JOIN Pessoa peAluno ON peAluno.id = a.id
                LEFT JOIN Notificacoes n ON n.pessoa.id = peAluno.id
                WHERE pc.dataFim IS NOT NULL
                  AND pc.dataFim BETWEEN CURRENT_DATE AND :duasSemanasDepois
                  AND pc.status NOT IN ('INATIVO', 'PENDENTE')
            """)
    List<NotificacaoPlanoVencimentoDto> findNotificacoesPlanoVencimento(
            @Param("duasSemanasDepois") LocalDate duasSemanasDepois
    );

    @Query("""
        SELECT count(pc)
        FROM PlanoContratado pc
        JOIN pc.plano p
        JOIN p.personalTrainer pt
        WHERE pt.id = :personalId
        AND pc.dataFim <= :limite
        AND pc.status IN ('ATIVO')
    """)
    Integer buscarDataFimIgualOuAntesDataLimite(LocalDate limite, Integer personalId);
}
