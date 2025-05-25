package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoTreinoPersonalDTO;
import tech.vitalis.caringu.entity.AlunoTreino;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.repository.AlunoTreinoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificacaoTreinoVencimentoService {

    private final AlunoTreinoRepository alunoTreinoRepository;
    private final NotificacoesRepository notificacoesRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;
    private final NotificacaoEnviarService notificacaoEnviarService;

    public NotificacaoTreinoVencimentoService(AlunoTreinoRepository alunoTreinoRepository, NotificacoesRepository notificacoesRepository, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository, NotificacaoEnviarService notificacaoEnviarService) {
        this.alunoTreinoRepository = alunoTreinoRepository;
        this.notificacoesRepository = notificacoesRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
        this.notificacaoEnviarService = notificacaoEnviarService;
    }

    public void enviarNotificacoesTreinosVencendo(){
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);

        List<AlunoTreino> alunoTreinos = alunoTreinoRepository.findByDataVencimentoBetween(hoje, daquiDuasSemanas);


        for (AlunoTreino alunoTreino : alunoTreinos){
            Pessoa pessoa = alunoTreino.getAlunos();

            boolean prefereReceber = preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                pessoa, TipoPreferenciaEnum.TREINO_PROXIMO_VENCIMENTO
            );

            if (!prefereReceber) continue;

            boolean notificacaoExiste = notificacoesRepository.existsByPessoaAndTipoAndTituloAndVisualizadaFalse(
                    pessoa,
                    TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO,
                    "Treino do aluno está perto de vencer!"
            );


            if (notificacaoExiste) continue;

            Notificacoes notificacao = new Notificacoes();
            notificacao.setPessoa(pessoa);
            notificacao.setTipo(TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO);
            notificacao.setTitulo("Treino do aluno está perto de vencer!");
            notificacao.setVisualizada(false);
            notificacao.setDataCriacao(LocalDateTime.now());

            notificacoesRepository.save(notificacao);
        }
    }

    public void notificarPersonaisTreinadores() {
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);

        List<NotificacaoTreinoPersonalDTO> notificacoes = alunoTreinoRepository.findTreinosVencendo(daquiDuasSemanas);

        Map<Integer, List<NotificacaoTreinoPersonalDTO>> porPersonal = notificacoes.stream()
                .collect(Collectors.groupingBy(NotificacaoTreinoPersonalDTO::personalTrainerId));

        for (Map.Entry<Integer, List<NotificacaoTreinoPersonalDTO>> entry : porPersonal.entrySet()) {
            Integer personalId = entry.getKey();
            List<NotificacaoTreinoPersonalDTO> itens = entry.getValue();

            String mensagem = montarMensagem(itens);

            notificacaoEnviarService.enviarNotificacao(personalId, mensagem);
        }
    }

    private String montarMensagem(List<NotificacaoTreinoPersonalDTO> itens) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você tem treinos vencendo nos próximos 14 dias para os seguintes alunos:\n");
        for (NotificacaoTreinoPersonalDTO item : itens) {
            sb.append("- Aluno ID: ").append(item.alunoId())
                    .append(", Data Vencimento: ").append(item.dataVencimento())
                    .append("\n");
        }
        return sb.toString();
    }

    public List<NotificacaoTreinoPersonalDTO> buscarTreinosVencendo(LocalDate dataLimite) {
        return alunoTreinoRepository.findTreinosVencendo(dataLimite);
    }

}
