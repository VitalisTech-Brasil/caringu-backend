package tech.vitalis.caringu.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto;
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.PlanoRepository;
import tech.vitalis.caringu.service.NotificacaoPlanoVencimentoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class NotificacaoEventoPublicacao {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoEventoPublicacao.class);
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final PlanoRepository planoRepository;
    private final PlanoContratadoRepository planoContratadoRepository;
    private final NotificacoesRepository notificacoesRepository;

    public NotificacaoEventoPublicacao(RabbitTemplate rabbitTemplate, @Value("${app.rabbitmq.exchange}") String exchange, PlanoRepository planoRepository, PlanoContratadoRepository planoContratadoRepository, NotificacoesRepository notificacoesRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.planoRepository = planoRepository;
        this.planoContratadoRepository = planoContratadoRepository;
        this.notificacoesRepository = notificacoesRepository;
    }

    public void publicarEventoPlanoVencimento(Integer pessoaId, String tipoPessoa, String nomePessoa, String email) {
        LocalDate dataVencimento = LocalDate.now().plusDays(7);
        String dataVencimentoStr = dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String titulo = tipoPessoa.equals("ALUNO") ?
                "Olá " + nomePessoa + ", seu plano vence em " + dataVencimentoStr :
                "Olá " + nomePessoa + ", seu plano de personal vence em " + dataVencimentoStr;

        Map<String, Object> evento = Map.of(
                "eventoTipo", "PLANO_VENCIMENTO",
                "tipoPessoa", tipoPessoa,
                "pessoaId", pessoaId,
                "nomePessoa", nomePessoa,
                "titulo", titulo,
                "dataVencimento", dataVencimentoStr,
                "email", email,
                "timestamp", LocalDateTime.now().toString(),
                "origem", "SISTEMA"
        );

        String routingKey = tipoPessoa.equalsIgnoreCase("ALUNO") ?
                "notificacao.plano.vencimento.aluno" :
                "notificacao.plano.vencimento.personal";

        logger.info("Publicando evento PLANO_VENCIMENTO: pessoa={}, tipo={}, nome={}",
                pessoaId, tipoPessoa, nomePessoa);
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }

    public void publicarEventoPlanoVencimentoComTitulo(
            Integer pessoaId,
            String tipoPessoa,
            String nomePessoa,
            String tituloCustomizado,
            LocalDate dataVencimento
    ) {
        String dataVencimentoStr = dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Map<String, Object> evento = Map.of(
                "eventoTipo", "PLANO_VENCIMENTO",
                "tipoPessoa", tipoPessoa,
                "pessoaId", pessoaId,
                "nomePessoa", nomePessoa,
                "titulo", tituloCustomizado,
                "dataVencimento", dataVencimentoStr,
                "timestamp", LocalDateTime.now().toString(),
                "origem", "SISTEMA_AUTOMATICO"
        );

        String routingKey = tipoPessoa.equalsIgnoreCase("ALUNO") ?
                "notificacao.plano.vencimento.aluno" :
                "notificacao.plano.vencimento.personal";

        logger.info("Publicando evento PLANO_VENCIMENTO: pessoa={}, tipo={}, nome={}, titulo={}",
                pessoaId, tipoPessoa, nomePessoa, tituloCustomizado);
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }

    /*
    public void enviarNotificacoesPlanosVencendo() {
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);

        List<NotificacaoPlanoVencimentoDto> planos = planoContratadoRepository.findNotificacoesPlanoVencimento(daquiDuasSemanas);

        for (NotificacaoPlanoVencimentoDto alunoPlano : planos) {
            Aluno alunoPessoa = alunoPlano.alunoId();
            PersonalTrainer personalTrainer = alunoPlano.personalTrainer();
            String nomePlano = alunoPlano.nomePlano();
            LocalDate dataVencimento = alunoPlano.dataFim();

            boolean existeNotificacaoPersonal = notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(
                    personalTrainer.getId(), TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);
            boolean existeNotificacaoAluno = notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(
                    alunoPessoa.getId(), TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);

            if (!existeNotificacaoAluno) {
                Notificacoes notAluno = new Notificacoes();
                notAluno.setPessoa(alunoPessoa);
                notAluno.setTipo(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);
                notAluno.setTitulo("Lembrete: o plano \"" + nomePlano + "\" vencerá em breve (Data: " + dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
                notAluno.setVisualizada(false);
                notAluno.setDataCriacao(LocalDateTime.now());
                notificacoesRepository.save(notAluno);
            }
            if(!existeNotificacaoPersonal){
                Notificacoes notPersonal = new Notificacoes();
                notPersonal.setPessoa(personalTrainer);
                notPersonal.setTipo(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO);
                notPersonal.setTitulo("Lembrete: o plano " + nomePlano + " do Aluno \"" + alunoPlano.alunoNome() + "\" vencerá em breve (Data: " + dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
                notPersonal.setVisualizada(false);
                notPersonal.setDataCriacao(LocalDateTime.now());
                notificacoesRepository.save(notPersonal);
            }
        }
    }

     */
}
