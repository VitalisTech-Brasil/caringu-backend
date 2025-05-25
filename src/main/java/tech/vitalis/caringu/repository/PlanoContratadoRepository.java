package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Aluno.PlanoPertoFimResponseDTO;
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
                SELECT new tech.vitalis.caringu.dtos.Aluno.PlanoPertoFimResponseDTO(
                    a.id,
                    a.nome,
                    a.urlFotoPerfil,
                    (p.quantidadeAulas - CAST(COUNT(at) AS int))
                )
                FROM PlanoContratado pc
                JOIN Plano p
                    ON pc.plano.id = p.id
                JOIN pc.aluno a
                LEFT JOIN AlunoTreino at ON at.alunos.id = a.id
                    AND at.dataHorarioFim IS NOT NULL
                    AND at.dataHorarioFim > pc.dataContratacao
                WHERE pc.status = 'ATIVO'
                    AND p.personalTrainer.id = :personalId
                GROUP BY a.id, a.nome, a.urlFotoPerfil, p.quantidadeAulas
                ORDER BY (p.quantidadeAulas - CAST(COUNT(at) AS int))
            """)
    List<PlanoPertoFimResponseDTO> buscarAlunosComPlanoPertoDoFim(@Param("personalId") Integer personalId);

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
                WHERE pc.status = "PENDENTE" AND p.personalTrainer.id = :personalId
            """)
    List<PlanoContratadoPendenteRequestDTO> listarSolicitacoesPendentes(@Param("personalId") Integer personalId);

}
