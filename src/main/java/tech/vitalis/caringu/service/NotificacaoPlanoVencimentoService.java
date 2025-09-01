package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificacaoPlanoVencimentoService {

    private final PlanoContratadoRepository planoContratadoRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;
    private final NotificacoesRepository notificacoesRepository;
    private final NotificacaoEnviarService notificacaoEnviarService;

    public NotificacaoPlanoVencimentoService(PlanoContratadoRepository planoContratadoRepository, PreferenciaNotificacaoRepository preferenciaNotificacaoRepository, NotificacoesRepository notificacoesRepository, NotificacaoEnviarService notificacaoEnviarService) {
        this.planoContratadoRepository = planoContratadoRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
        this.notificacoesRepository = notificacoesRepository;
        this.notificacaoEnviarService = notificacaoEnviarService;
    }

    public void enviarNotificacoesPlanoVencimento(){
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemans = hoje.plusWeeks(2);

        List<PlanoContratado> planoContratados = planoContratadoRepository.findByDataFimBetweenAndStatus(hoje, daquiDuasSemans, StatusEnum.ATIVO);

        for (PlanoContratado planoContratado : planoContratados){
            Pessoa alunoPessoa = planoContratado.getAluno();
            Plano plano = planoContratado.getPlano();
            PersonalTrainer personal = plano.getPersonalTrainer();

            boolean alunoPrefereReceber = preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                    alunoPessoa, TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO);

            boolean personalPrefereReceber = preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                    personal, TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO);

            boolean existeNotificacaoAluno = notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(
                    alunoPessoa.getId(), TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);

            boolean existeNotificacaoPersonal = notificacoesRepository.existsByPersonalIdAndTipoAndVisualizadaFalse(
                    personal.getId(), TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);

            if (alunoPrefereReceber && !existeNotificacaoAluno) {
                Notificacoes notAluno = new Notificacoes();
                notAluno.setPessoa(alunoPessoa);
                notAluno.setTipo(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);
                notAluno.setTitulo("Lembrete: o plano \"" + plano.getNome() + "\" vencerá em breve (Data: " + planoContratado.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
                notAluno.setVisualizada(false);
                notAluno.setDataCriacao(LocalDateTime.now());
                notificacoesRepository.save(notAluno);
            }

            // Notificação para o personal
            if (personalPrefereReceber && !existeNotificacaoPersonal) {
                Notificacoes notPersonal = new Notificacoes();
                notPersonal.setPessoa(personal);
                notPersonal.setTipo(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);
                notPersonal.setTitulo("O plano " + plano.getNome() + " do(a) aluno(a) " + alunoPessoa.getNome() + " vencerá em breve (" + planoContratado.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
                notPersonal.setVisualizada(false);
                notPersonal.setDataCriacao(LocalDateTime.now());
                notificacoesRepository.save(notPersonal);
            }
        }
    }

//    public void notificarPersonais(){
//        LocalDate hoje = LocalDate.now();
//        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);
//
//        List<NotificacaoPlanoVencimentoDto> notificacoes =
//                planoContratadoRepository.findNotificacoesPlanoVencimento(daquiDuasSemanas);
//
//        Map<Integer, List<NotificacaoPlanoVencimentoDto>> porPersonal = notificacoes.stream()
//                .collect(Collectors.groupingBy(NotificacaoPlanoVencimentoDto::personalTrainerId));
//
//        for (Map.Entry<Integer, List<NotificacaoPlanoVencimentoDto>> entry : porPersonal.entrySet()) {
//            Integer personalId = entry.getKey();
//            List<NotificacaoPlanoVencimentoDto> itens = entry.getValue();
//
//            String mensagem = montarMensagem(itens);
//
//            notificacaoEnviarService.enviarNotificacao(personalId, mensagem);
//        }
//    }

    private String montarMensagem(List<NotificacaoPlanoVencimentoDto> itens) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você tem planos vencendo nos próximos 14 dias para os seguintes alunos:\n");
        for (NotificacaoPlanoVencimentoDto item : itens) {
            sb.append("- Aluno ID: ").append(item.alunoId())
                    .append(", Data Vencimento: ").append(item.dataFim())
                    .append("\n");
        }
        return sb.toString();
    }

//    public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimento(LocalDate datalimite){
//        return planoContratadoRepository.findNotificacoesPlanoVencimento(datalimite);
//    }
//
//    public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimentoPorPersonal(LocalDate dataLimite, Integer personalId){
//        return planoContratadoRepository.findNotificacoesPlanoVencimentoPorPersonal(dataLimite,personalId);
//    }

}
