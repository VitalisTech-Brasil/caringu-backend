package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.core.exceptions.ApiExceptions;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDateTime;

@Service
public class NotificacaoRecebimentoService {

    private final NotificacoesRepository notificacoesRepository;
    private final PlanoContratadoRepository planoContratadoRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    public NotificacaoRecebimentoService(NotificacoesRepository notificacoesRepository, PlanoContratadoRepository planoContratadoRepository, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository) {
        this.notificacoesRepository = notificacoesRepository;
        this.planoContratadoRepository = planoContratadoRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
    }

    @Transactional
    public void notificarPagamentoConfirmado(Integer planoContratadoId){
        PlanoContratadoEntity planoContratado = buscarPlanoContratado(planoContratadoId);

        Integer alunoId = planoContratado.getAluno().getId();

        boolean preferenciaNotificacao = preferenciaNotificacaoRepository.
                existsByPessoaIdAndTipoAndAtivadaTrue(alunoId, TipoPreferenciaEnum.PAGAMENTO_REALIZADO);

        if(!preferenciaNotificacao){
            return;
        }

        String nomePlano = planoContratado.getPlano().getNome();
        String nomePersonal = planoContratado.getPlano().getPersonalTrainer().getNome();

        String titulo = String.format(
                "Pagamento do plano '%s' confirmado por %s. Seu plano está ativo!",
                nomePlano,
                nomePersonal
        );

        Notificacoes notificacao = new Notificacoes(
                null,
                planoContratado.getAluno(),
                TipoNotificacaoEnum.PAGAMENTO_REALIZADO,
                titulo,
                false,
                LocalDateTime.now()
        );

        notificacoesRepository.save(notificacao);
    }

    @Transactional
    public void notificarPagamentoNegado(Integer planoContratadoId, String motivo){
        PlanoContratadoEntity planoContratado = buscarPlanoContratado(planoContratadoId);

        Integer alunoId = planoContratado.getAluno().getId();

        boolean preferenciaNotificacao = preferenciaNotificacaoRepository.
                existsByPessoaIdAndTipoAndAtivadaTrue(alunoId, TipoPreferenciaEnum.PAGAMENTO_CANCELADO);

        if(!preferenciaNotificacao){
            return;
        }

        String nomePlano = planoContratado.getPlano().getNome();
        String nomePersonal = planoContratado.getPlano().getPersonalTrainer().getNome();

        String titulo = String.format(
                "Pagamento do plano '%s' não foi confirmado por %s.%s Entre em contato para resolver.",
                nomePlano,
                nomePersonal,
                motivo != null && !motivo.isBlank() ? " Motivo: " + motivo + "." : ""
        );

        Notificacoes notificacao = new Notificacoes(
                null,
                planoContratado.getAluno(),
                TipoNotificacaoEnum.PAGAMENTO_CANCELADO,
                titulo,
                false,
                LocalDateTime.now()
        );

        notificacoesRepository.save(notificacao);
    }

    private PlanoContratadoEntity buscarPlanoContratado(Integer planoContratadoId){
        return planoContratadoRepository.findById(planoContratadoId)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException(
                        "Plano contratado com ID " + planoContratadoId + " não encontrado"
                ));
    }
}
