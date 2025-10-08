package tech.vitalis.caringu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.consumer.NotificacaoEventoPublicacao;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificacaoPlanoVencimentoService {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoPlanoVencimentoService.class);

    private final PlanoContratadoRepository planoContratadoRepository;
    private final PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;
    private final NotificacoesRepository notificacoesRepository;
    private final NotificacaoEventoPublicacao notificacaoPublicador;

    public NotificacaoPlanoVencimentoService(
            PlanoContratadoRepository planoContratadoRepository,
            PreferenciaNotificacaoRepository preferenciaNotificacaoRepository,
            NotificacoesRepository notificacoesRepository, // Manter para verificações
            NotificacaoEventoPublicacao notificacaoPublicador) {
        this.planoContratadoRepository = planoContratadoRepository;
        this.preferenciaNotificacaoRepository = preferenciaNotificacaoRepository;
        this.notificacoesRepository = notificacoesRepository;
        this.notificacaoPublicador = notificacaoPublicador;
    }

    public void enviarNotificacoesPlanoVencimento(){
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemanas =  hoje.plusWeeks(2);

        logger.info("🔍 Verificando planos vencendo entre {} e {}", hoje, daquiDuasSemanas);

        List<PlanoContratado> planoContratados = planoContratadoRepository
                .findByDataFimBetweenAndStatus(hoje, daquiDuasSemanas, StatusEnum.ATIVO);

        logger.info("📊 Encontrados {} planos próximos do vencimento", planoContratados.size());

        for (PlanoContratado planoContratado : planoContratados){
            processarPlanoVencimento(planoContratado);
        }

        logger.info("✅ Processamento de planos vencimento concluído");
    }
  
      public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimento(LocalDate datalimite){
        return planoContratadoRepository.findNotificacoesPlanoVencimento(datalimite);
    }

    public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimentoPorPersonal(LocalDate dataLimite, Integer personalId){
        return planoContratadoRepository.findNotificacoesPlanoVencimentoPorPersonal(dataLimite, personalId);
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
  
  private void processarPlanoVencimento(PlanoContratado planoContratado) {
        Pessoa alunoPessoa = planoContratado.getAluno();
        Plano plano = planoContratado.getPlano();
        PersonalTrainer personal = plano.getPersonalTrainer();

        boolean alunoPrefereReceber = preferenciaNotificacaoRepository
                .existsByPessoaAndTipoAndAtivadaTrue(alunoPessoa, TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO);

        boolean personalPrefereReceber = preferenciaNotificacaoRepository
                .existsByPessoaAndTipoAndAtivadaTrue(personal, TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO);

        boolean existeNotificacaoAluno = notificacoesRepository
                .existsByAlunoIdAndTipoAndVisualizadaFalse(alunoPessoa.getId(), TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);

        boolean existeNotificacaoPersonal = notificacoesRepository
                .existsByPersonalIdAndTipoAndVisualizadaFalse(personal.getId(), TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);

        if (alunoPrefereReceber && !existeNotificacaoAluno) {
            String tituloAluno = "Lembrete: o plano \"" + plano.getNome() + "\" vencerá em breve (Data: " +
                    planoContratado.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")";

            logger.info("Publicando evento de plano vencimento para ALUNO: {}", alunoPessoa.getId());

            notificacaoPublicador.publicarEventoPlanoVencimentoComTitulo(
                    alunoPessoa.getId(),
                    "ALUNO",
                    alunoPessoa.getNome(),
                    tituloAluno,
                    planoContratado.getDataFim()
            );
        } else if (existeNotificacaoAluno) {
            logger.info("Notificação de plano já existe para ALUNO: {}", alunoPessoa.getId());
        }

        // PROCESSAMENTO PARA PERSONAL
        if (personalPrefereReceber && !existeNotificacaoPersonal) {
            String tituloPersonal = "O plano \"" + plano.getNome() + "\" do(a) aluno(a) " + alunoPessoa.getNome() +
                    " vencerá em breve (" + planoContratado.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")";

            logger.info("Publicando evento de plano vencimento para PERSONAL: {}", personal.getId());

            notificacaoPublicador.publicarEventoPlanoVencimentoComTitulo(
                    personal.getId(),
                    "PERSONAL",
                    personal.getNome(),
                    tituloPersonal,
                    planoContratado.getDataFim()
            );
        } else if (existeNotificacaoPersonal) {
            logger.info("Notificação de plano já existe para PERSONAL: {}", personal.getId());
        }
    }

//    public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimento(LocalDate datalimite){
//        return planoContratadoRepository.findNotificacoesPlanoVencimento(datalimite);
//    }
//
//    public List<NotificacaoPlanoVencimentoDto> buscarNotificacoesPlanoVencimentoPorPersonal(LocalDate dataLimite, Integer personalId){
//        return planoContratadoRepository.findNotificacoesPlanoVencimentoPorPersonal(dataLimite,personalId);
//    }

}

