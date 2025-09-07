package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPagamentoPendenteResponseDTO;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoPendenteRequestDTO;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.PlanoContratado.PlanoContratadoNaoEncontradoException;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;
import tech.vitalis.caringu.strategy.PlanoContratado.StatusEnumValidationStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class PlanoContratadoService {

    private final PlanoContratadoRepository planoContratadoRepository;
    private final NotificacoesRepository notificacoesRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;
    private final AlunoRepository alunoRepository;

    public PlanoContratadoService(PlanoContratadoRepository planoContratadoRepository, NotificacoesRepository notificacoesRepository, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository, AlunoRepository alunoRepository) {
        this.planoContratadoRepository = planoContratadoRepository;
        this.notificacoesRepository = notificacoesRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<PlanoContratadoPendenteRequestDTO> listarSolicitacoesPendentes(Integer personalId) {
        return planoContratadoRepository.listarSolicitacoesPendentes(personalId);
    }

    public void atualizarStatus(Integer idPlanoContratado, StatusEnum novoStatus) {
        PlanoContratado planoContratado = planoContratadoRepository.findById(idPlanoContratado)
                .orElseThrow(() -> new PlanoContratadoNaoEncontradoException("Plano contratado com id %d não encontrado.".formatted(idPlanoContratado)));

        validarEnums(Map.of(
                new StatusEnumValidationStrategy(), novoStatus
        ));

        if (novoStatus.equals(StatusEnum.EM_PROCESSO)) {

            Plano plano = planoContratado.getPlano();
            PersonalTrainer personal = plano.getPersonalTrainer();

            boolean deveNotificar = preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(personal.getId(), TipoPreferenciaEnum.PAGAMENTO_REALIZADO);

            if (deveNotificar) {
                Notificacoes notificacao = new Notificacoes();
                notificacao.setPessoa(personal);
                notificacao.setTipo(TipoNotificacaoEnum.PAGAMENTO_REALIZADO);

                String nomeAluno = planoContratado.getAluno().getNome();

                notificacao.setTitulo("Pagamento do plano '%s' confirmado por %s, aguardando validação.".formatted(plano.getNome(), nomeAluno));
                notificacao.setDataCriacao(LocalDateTime.now());

                notificacoesRepository.save(notificacao);
            }
        }

        if (novoStatus.equals(StatusEnum.ATIVO)) {
            boolean isAvulso = planoContratado.getPlano().getPeriodo().equals(PeriodoEnum.AVULSO);
            boolean isMensal = planoContratado.getPlano().getPeriodo().equals(PeriodoEnum.MENSAL);
            boolean isSemestral = planoContratado.getPlano().getPeriodo().equals(PeriodoEnum.SEMESTRAL);

            if (!isAvulso) {
                planoContratado.setDataContratacao(LocalDate.now());

                if (isMensal) {
                    planoContratado.setDataFim(LocalDate.now().plusMonths(1));

                } else if (isSemestral) {
                    planoContratado.setDataFim(LocalDate.now().plusMonths(6));
                }
            }
        }

        if (novoStatus.equals(StatusEnum.CANCELADO)) {
            planoContratado.setDataFim(LocalDate.now());
        }

        planoContratado.setStatus(novoStatus);
        planoContratadoRepository.save(planoContratado);
    }

    public List<PlanoContratadoPagamentoPendenteResponseDTO> verificarContratacaoPendentePorAluno(Integer alunosId) {
        Aluno aluno = alunoRepository.findById(alunosId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID %d não encontrado.".formatted(alunosId)));

        return planoContratadoRepository.buscarPorAlunoIdStatus(alunosId);
    }
}
