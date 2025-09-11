package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoTreinoPersonalDTO;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.repository.AlunoTreinoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

//    public void enviarNotificacoesTreinosVencendo() {
//        LocalDate hoje = LocalDate.now();
//        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);
//
//        List<AlunoTreino> alunoTreinos = alunoTreinoRepository.findByDataVencimentoBetween(hoje, daquiDuasSemanas);
//
//        for (AlunoTreino alunoTreino : alunoTreinos) {
//            Pessoa alunoPessoa = alunoTreino.getAlunos();
//            TreinoExercicio treino = alunoTreino.getTreinosExercicios();
//            PersonalTrainer personal = treino.getTreinos().getPersonal();
//            Pessoa pessoaPersonal = personal;
//            Treino treinoObj = treino.getTreinos();
//            String nomeTreino = treinoObj.getNome();
//
//            LocalDate dataVencimento = alunoTreino.getDataVencimento();
//
//            // Preferência de notificação
//            /*
//            boolean alunoPrefereReceber = preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
//                    alunoPessoa, TipoPreferenciaEnum.TREINO_PROXIMO_VENCIMENTO);
//
//            boolean personalPrefereReceber = preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
//                    pessoaPersonal, TipoPreferenciaEnum.TREINO_PROXIMO_VENCIMENTO);
//
//             */
//
//            boolean existeNotificacaoAluno = notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(
//                    alunoTreino.getAlunos().getId(), TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO);
//
//            boolean existeNotificacaoPersonal = notificacoesRepository.existsByPersonalIdAndTipoAndVisualizadaFalse(
//                    personal.getId(), TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO);
//
//
//            // Notificação para o aluno
//            if (!existeNotificacaoAluno) {
//                Notificacoes notAluno = new Notificacoes();
//                notAluno.setPessoa(alunoPessoa);
//                notAluno.setTipo(TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO);
//                notAluno.setTitulo("Lembrete: o treino \"" + nomeTreino + "\" vencerá em breve (Data: " + dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
//                notAluno.setVisualizada(false);
//                notAluno.setDataCriacao(LocalDateTime.now());
//                notificacoesRepository.save(notAluno);
//            }
//
//            // Notificação para o personal
//            if (!existeNotificacaoPersonal) {
//                Notificacoes notPersonal = new Notificacoes();
//                notPersonal.setPessoa(pessoaPersonal);
//                notPersonal.setTipo(TipoNotificacaoEnum.TREINO_PROXIMO_VENCIMENTO);
//                notPersonal.setTitulo("O treino " + nomeTreino + " do aluno " + alunoPessoa.getNome() + " vencerá em breve (" + dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
//                notPersonal.setVisualizada(false);
//                notPersonal.setDataCriacao(LocalDateTime.now());
//                notificacoesRepository.save(notPersonal);
//            }
//        }
//    }


//    public void notificarPersonaisTreinadores() {
//        LocalDate hoje = LocalDate.now();
//        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);
//
//        List<NotificacaoTreinoPersonalDTO> notificacoes = alunoTreinoRepository.findTreinosVencendo(daquiDuasSemanas);
//
//        Map<Integer, List<NotificacaoTreinoPersonalDTO>> porPersonal = notificacoes.stream()
//                .collect(Collectors.groupingBy(NotificacaoTreinoPersonalDTO::personalTrainerId));
//
//        for (Map.Entry<Integer, List<NotificacaoTreinoPersonalDTO>> entry : porPersonal.entrySet()) {
//            Integer personalId = entry.getKey();
//            List<NotificacaoTreinoPersonalDTO> itens = entry.getValue();
//
//            String mensagem = montarMensagem(itens);
//
//            notificacaoEnviarService.enviarNotificacao(personalId, mensagem);
//        }
//    }

//    public void notificarPorPersonaisTreinadores(Integer personalId) {
//        LocalDate hoje = LocalDate.now();
//        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);
//
//        List<NotificacaoTreinoPersonalDTO> notificacoes = buscarTreinosVencendoPorPersonal(daquiDuasSemanas, personalId);
//
//        if (notificacoes.isEmpty()) return;
//
//        String mensagem = montarMensagem(notificacoes);
//
//        notificacaoEnviarService.enviarNotificacao(personalId, mensagem);
//    }


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

//    public List<NotificacaoTreinoPersonalDTO> buscarTreinosVencendo(LocalDate dataLimite) {
//        return alunoTreinoRepository.findTreinosVencendo(dataLimite);
//    }

//    public List<NotificacaoTreinoPersonalDTO> buscarTreinosVencendoPorPersonal(LocalDate dataLimite, Integer personalId) {
//        return alunoTreinoRepository.findTreinosVencendoPorPersonal(dataLimite, personalId);
//    }
}
