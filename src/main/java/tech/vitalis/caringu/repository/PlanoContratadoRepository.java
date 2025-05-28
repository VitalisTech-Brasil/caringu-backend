package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.entity.PlanoContratado;

import java.util.List;

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

}
