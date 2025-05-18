package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.PreferenciaNotificacao;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.exception.PreferenciasNotificacao.PreferenciasNotificacaoNaoEncontradaException;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class PreferenciaNotificacaoService {

    private final PreferenciaNotificacaoRepository repository;

    public PreferenciaNotificacaoService(PreferenciaNotificacaoRepository repository) {
        this.repository = repository;
    }

    public List<PreferenciaNotificacao> listarPorPessoa(Integer pessoaId) {
        return repository.findByPessoaId(pessoaId);
    }

    public void criarPreferenciasPadrao(Pessoa pessoa) {
        List<PreferenciaNotificacao> preferencias = Arrays.stream(TipoPreferenciaEnum.values())
                .map(tipo -> {
                    PreferenciaNotificacao p = new PreferenciaNotificacao();
                    p.setPessoa(pessoa);
                    p.setTipo(tipo);
                    p.setAtivada(true);
                    return p;
                })
                .toList();

        repository.saveAll(preferencias);
    }

    public PreferenciaNotificacao atualizarPreferencia(Integer pessoaId, TipoPreferenciaEnum tipo, boolean ativada) {
        PreferenciaNotificacao preferencia = repository.findByPessoaId(pessoaId).stream()
                .filter(p -> p.getTipo() == tipo)
                .findFirst()
                .orElseThrow(() -> new PreferenciasNotificacaoNaoEncontradaException("Preferência não encontrada."));

        preferencia.setAtivada(ativada);
        return repository.save(preferencia);
    }
}
