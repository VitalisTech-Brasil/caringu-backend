package tech.vitalis.caringu.core.application.usecases.planoContratado;

import tech.vitalis.caringu.core.application.gateways.planoContratado.PlanoContratadoGateway;
import tech.vitalis.caringu.core.domain.entity.PlanoContratado;
import tech.vitalis.caringu.core.exceptions.aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.core.exceptions.planoContratado.PlanoContratadoNaoEncontradoException;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.infrastructure.web.planoContratado.GetPlanoContratadoPagamentoPendenteResponse;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;
import tech.vitalis.caringu.service.NotificacaoRecebimentoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PlanoContratadoUseCase {
    private final PlanoContratadoGateway planoContratadoGateway;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;
    private final NotificacoesRepository notificacoesRepository;
    private final AlunoRepository alunoRepository;
    private final NotificacaoRecebimentoService notificacaoRecebimentoService;

    public PlanoContratadoUseCase(PlanoContratadoGateway planoContratadoGateway, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository, NotificacoesRepository notificacoesRepository, AlunoRepository alunoRepository, NotificacaoRecebimentoService notificacaoRecebimentoService) {
        this.planoContratadoGateway = planoContratadoGateway;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
        this.notificacoesRepository = notificacoesRepository;
        this.alunoRepository = alunoRepository;
        this.notificacaoRecebimentoService = notificacaoRecebimentoService;
    }

    public List<PlanoContratado> listSolicitacoesPendentesInteractor(Integer personalId){
        return planoContratadoGateway.listSolicitacoesPendentes(personalId);
    }

    public Integer findQtdPlanosVencendoInteractor(LocalDate dataLimite, Integer personalId){
        return planoContratadoGateway.countPlanosVencendoAte(dataLimite, personalId);
    }

    public List<PlanoContratado> verificarContratacaoPendentePorAlunoInteractor(Integer alunoId){
        alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException(
                        "Aluno com ID %d não encontrado.".formatted(alunoId)
                ));

        return planoContratadoGateway.findPorAlunoIdEStatus(alunoId);
    }

    // Endpoints para atualizacao do plano
    public PlanoContratado updateStatusPlanoInteractor(Integer planoContratadoId, StatusEnum novoStatus){
        PlanoContratado planoContratado = planoContratadoGateway.getPlanoContratadoById(planoContratadoId)
                .orElseThrow(() -> new PlanoContratadoNaoEncontradoException(
                        "Plano contratado com id %d não encontrado.".formatted(planoContratadoId)
                ));

        PlanoContratado planoAtualizado = switch (novoStatus){
            case EM_PROCESSO -> {
                enviarNotificacaoPagamento(planoContratado);
                yield planoContratado.emProcesso();
            }
            case ATIVO -> {
                PlanoContratado planoAtivado = planoContratado.ativar();

                notificacaoRecebimentoService.notificarPagamentoConfirmado(planoContratadoId);

                yield planoAtivado;
            }
            case CANCELADO -> {
                PlanoContratado planoCancelado = planoContratado.cancelar();

                notificacaoRecebimentoService.notificarPagamentoNegado(
                        planoContratadoId,
                        "Pagamento negado pelo personal trainer."
                );

                yield planoCancelado;
            }
            default -> throw new IllegalArgumentException("Status inválido: " + novoStatus);
        };

        return planoContratadoGateway.update(planoAtualizado);
    }

    private void enviarNotificacaoPagamento(PlanoContratado planoContratado){
        Integer personalId = planoContratado.plano().getPersonalTrainer().getId();

        boolean deveNotificar = preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(personalId, TipoPreferenciaEnum.PAGAMENTO_REALIZADO);

        if (deveNotificar){
            String titulo = "Pagamento do plano '%s' confirmado por %s, aguardando validação."
                    .formatted(
                            planoContratado.plano().getNome(),
                            planoContratado.aluno().getNome()
                    );

            Notificacoes notificacao = new Notificacoes(
                    null,
                    planoContratado.plano().getPersonalTrainer(),
                    TipoNotificacaoEnum.PAGAMENTO_REALIZADO,
                    titulo,
                    false,
                    LocalDateTime.now()
            );
            notificacoesRepository.save(notificacao);
        }
    }
}
