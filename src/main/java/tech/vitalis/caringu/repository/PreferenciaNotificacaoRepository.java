package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.PreferenciaNotificacao;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;

import java.util.List;

@Repository
public interface PreferenciaNotificacaoRepository extends JpaRepository<PreferenciaNotificacao, Integer> {
    List<PreferenciaNotificacao> findByPessoaId(Integer pessoaId);

    boolean existsByPessoaAndTipoAndAtivadaTrue(Pessoa pessoa, TipoPreferenciaEnum tipo);

    boolean existsByPessoaIdAndTipoAndAtivadaTrue(Integer pessoaId, TipoPreferenciaEnum tipo);
}