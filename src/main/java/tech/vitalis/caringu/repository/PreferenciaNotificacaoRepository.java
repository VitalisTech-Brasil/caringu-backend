package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.PreferenciaNotificacao;

import java.util.List;

@Repository
public interface PreferenciaNotificacaoRepository extends JpaRepository<PreferenciaNotificacao, Integer> {
    List<PreferenciaNotificacao> findByPessoaId(Integer pessoaId);
}