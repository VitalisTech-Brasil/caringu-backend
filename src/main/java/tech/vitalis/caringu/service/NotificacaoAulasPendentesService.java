package tech.vitalis.caringu.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aula.AulasPendentesNotificacaoDTO;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.enums.TipoAutorEnum;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacaoAulasPendentesService {
    private final AulaRepository aulaRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final NotificacoesRepository notificacoesRepository;

    public NotificacaoAulasPendentesService(AulaRepository aulaRepository, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository, PessoaRepository pessoaRepository, NotificacoesRepository notificacoesRepository) {
        this.aulaRepository = aulaRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.notificacoesRepository = notificacoesRepository;
    }

    @Scheduled(cron = "0 0 9 * * MON", zone = "America/Sao_Paulo")
    public void notificarAulasPendentesSemanalmente(){
        List<AulasPendentesNotificacaoDTO> planosComAulasPendentes = buscarPlanosComAulasPendentes();

        if (planosComAulasPendentes.isEmpty()){
            return;
        }

        List<Notificacoes> notificacoes = new ArrayList<>();

        for (AulasPendentesNotificacaoDTO plano : planosComAulasPendentes){
            // Notificação para o ALUNO
            Pessoa aluno = pessoaRepository.findById(plano.alunoId()).orElse(null);
            if (aluno != null) {
                notificacoes.add(criarNotificacao(aluno, plano.mensagemAluno()));
            }

            // Notificação para o PERSONAL
            Pessoa personal = pessoaRepository.findById(plano.personalId()).orElse(null);
            if (personal != null) {
                notificacoes.add(criarNotificacao(personal, plano.mensagemPersonal()));
            }
        }

        notificacoesRepository.saveAll(notificacoes);
    }

    public List<AulasPendentesNotificacaoDTO> buscarPlanosComAulasPendentes(){
        List<AulasPendentesNotificacaoDTO> todosPlanos = aulaRepository.buscarTodosPlanosAtivos();

        List<AulasPendentesNotificacaoDTO> planosComAulasPendentes = todosPlanos.stream()
                .filter(plano -> plano.aulasDisponiveis() > 0)
                .sorted((p1, p2) -> Long.compare(p2.aulasDisponiveis(), p1.aulasDisponiveis()))
                .toList();

        return planosComAulasPendentes;
    }

    private Notificacoes criarNotificacao(Pessoa pessoa, String mensagem){
        Notificacoes notificacao = new Notificacoes();
        notificacao.setPessoa(pessoa);
        notificacao.setTipo(TipoNotificacaoEnum.AULAS_PENDENTES);
        notificacao.setTitulo(mensagem);
        notificacao.setVisualizada(false);
        notificacao.setDataCriacao(LocalDateTime.now());

        return notificacao;
    }
}
