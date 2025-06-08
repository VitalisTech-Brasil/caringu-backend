package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;

import java.util.List;

@Repository
public interface NotificacoesRepository extends JpaRepository<Notificacoes, Integer> {
    List<Notificacoes> findByPessoa_Id(Integer pessoasId);

    boolean existsByPessoaAndTipoAndTituloAndVisualizadaFalse(Pessoa pessoa, TipoNotificacaoEnum tipoNotificacaoEnum, String titulo);

    boolean existsByPessoaAndTipoAndVisualizadaFalse(Pessoa pessoa, TipoNotificacaoEnum tipo);

    @Query("""
    SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END
    FROM Notificacoes n
    JOIN Aluno a ON n.pessoa.id = a.id
    WHERE a.id = :alunoId
      AND n.tipo = :tipo
      AND n.visualizada = false
    """)
    boolean existsByAlunoIdAndTipoAndVisualizadaFalse(@Param("alunoId") Integer alunoId,
                                                      @Param("tipo") TipoNotificacaoEnum tipo);

    @Query("""
    SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END
    FROM Notificacoes n
    JOIN PersonalTrainer pt ON n.pessoa.id = pt.id
    WHERE pt.id = :personalId
      AND n.tipo = :tipo
      AND n.visualizada = false
    """)
    boolean existsByPersonalIdAndTipoAndVisualizadaFalse(@Param("personalId") Integer personalId,
                                                         @Param("tipo") TipoNotificacaoEnum tipo);

    List<Notificacoes> findByPessoaAndTipoAndVisualizadaFalse(Pessoa pessoa, TipoNotificacaoEnum tipoNotificacaoEnum);

    List<Notificacoes> findByPessoaOrderByVisualizadaAscDataCriacaoDesc(Pessoa pessoa);
    List<Notificacoes> findByPessoaAndVisualizadaFalse(Pessoa pessoa);

}