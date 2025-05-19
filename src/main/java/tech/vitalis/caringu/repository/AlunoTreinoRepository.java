package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.AlunoTreino;

import java.time.LocalDate;

@Repository
public interface AlunoTreinoRepository extends JpaRepository<AlunoTreino, Integer> {

    @Query("SELECT COUNT(at) FROM AlunoTreino at " +
            "JOIN at.alunos a " +
            "JOIN PlanoContratado pc ON pc.aluno.id = a.id " +
            "JOIN pc.plano p " +
            "WHERE at.dataVencimento BETWEEN CURRENT_DATE AND :dataLimite " +
            "AND p.personalTrainer.id = :personalId " +
            "AND pc.status = 'ATIVO'")
    Integer countTreinosProximosVencimento(@Param("personalId") Integer personalId, @Param("dataLimite") LocalDate dataLimite);
}
