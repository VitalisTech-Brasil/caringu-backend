package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPagamentoPendenteResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.StatusEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoContratadoRepository extends JpaRepository<PlanoContratado, Integer> {

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

}
