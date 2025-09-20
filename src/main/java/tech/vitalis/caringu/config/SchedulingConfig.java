package tech.vitalis.caringu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tech.vitalis.caringu.consumer.NotificacaoEventoPublicacao;
import tech.vitalis.caringu.service.NotificacaoPlanoVencimentoService;
import tech.vitalis.caringu.service.NotificacaoTreinoVencimentoService;
import tech.vitalis.caringu.service.NotificacoesService;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    private static final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);
    private final NotificacaoPlanoVencimentoService notificacaoPlanoVencimentoService;

    public SchedulingConfig(NotificacaoPlanoVencimentoService notificacaoPlanoVencimentoService) {
        this.notificacaoPlanoVencimentoService = notificacaoPlanoVencimentoService;
    }

    @Scheduled(cron = "0 0 8,13,22 * * *")
    public void verificarTreinosVencendo(){
        logger.info("Executando verificarTreinosVencendo em {}", LocalDateTime.now());
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();
    }
}
