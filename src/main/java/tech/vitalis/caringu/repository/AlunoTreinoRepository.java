package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoTreinoPersonalDTO;
import tech.vitalis.caringu.entity.AlunoTreino;

import java.time.LocalDate;
import java.util.List;

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

    List<AlunoTreino> findByDataVencimentoBetween(LocalDate hoje, LocalDate daquiDuasSemanas);


    @Query("SELECT DISTINCT new tech.vitalis.caringu.dtos.Notificacoes.NotificacaoTreinoPersonalDTO(" +
            "pt.id, pt.cref, a.id, at.dataVencimento) " +
            "FROM AlunoTreino at " +
            "JOIN at.alunos a " +
            "JOIN PlanoContratado pc ON pc.aluno.id = a.id " +
            "JOIN pc.plano p " +
            "JOIN p.personalTrainer pt " +
            "WHERE at.dataVencimento BETWEEN CURRENT_DATE AND :dataLimite " +
            "AND pc.status = 'ATIVO'")
    List<NotificacaoTreinoPersonalDTO> findTreinosVencendo(@Param("dataLimite") LocalDate dataLimite);


}
