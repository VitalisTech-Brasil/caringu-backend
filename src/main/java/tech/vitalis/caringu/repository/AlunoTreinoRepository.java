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
            "pt.id, pePersonal.nome, a.id, peAluno.nome, t.nome, n.titulo, ata.dataVencimento, n.dataCriacao, n.visualizada) " +
            "FROM AlunoTreino ata " +
            "JOIN ata.alunos a " +
            "JOIN PlanoContratado pc ON pc.aluno.id = a.id " +
            "JOIN pc.plano p " +
            "JOIN p.personalTrainer pt " +
            "JOIN Pessoa pePersonal ON pePersonal.id = pt.id " +
            "JOIN ata.treinosExercicios te " +
            "JOIN te.treinos t " +
            "JOIN Pessoa peAluno ON peAluno.id = a.id " +
            "LEFT JOIN Notificacoes n ON n.pessoa.id = peAluno.id " +
            "WHERE ata.dataVencimento BETWEEN CURRENT_DATE AND :dataLimite " +
            "AND n.titulo IS NOT NULL " +
            "AND pc.status = 'ATIVO'")
    List<NotificacaoTreinoPersonalDTO> findTreinosVencendo(@Param("dataLimite") LocalDate dataLimite);


    @Query("SELECT DISTINCT new tech.vitalis.caringu.dtos.Notificacoes.NotificacaoTreinoPersonalDTO(" +
            "pt.id, pePersonal.nome, a.id, peAluno.nome, t.nome, n.titulo, ata.dataVencimento, n.dataCriacao, n.visualizada) " +
            "FROM AlunoTreino ata " +
            "JOIN ata.alunos a " +
            "JOIN PlanoContratado pc ON pc.aluno.id = a.id " +
            "JOIN pc.plano p " +
            "JOIN p.personalTrainer pt " +
            "JOIN Pessoa pePersonal ON pePersonal.id = pt.id " +
            "JOIN ata.treinosExercicios te " +
            "JOIN te.treinos t " +
            "JOIN Pessoa peAluno ON peAluno.id = a.id " +
            "LEFT JOIN Notificacoes n ON n.pessoa.id = peAluno.id " +
            "WHERE ata.dataVencimento BETWEEN CURRENT_DATE AND :dataLimite " +
            "AND n.titulo IS NOT NULL " +
            "AND p.personalTrainer.id = :personalId " +
            "AND pc.status = 'ATIVO'")
    List<NotificacaoTreinoPersonalDTO> findTreinosVencendoPorPersonal(@Param("dataLimite") LocalDate dataLimite,
                                                                      @Param("personalId") Integer personalId);
}