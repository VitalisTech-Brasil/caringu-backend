package tech.vitalis.caringu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tech.vitalis.caringu.service.NotificacaoTreinoVencimentoService;
import tech.vitalis.caringu.service.NotificacoesService;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    private static final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);
    private final NotificacaoTreinoVencimentoService notificacoesService;

    public SchedulingConfig(NotificacaoTreinoVencimentoService notificacoesService) {
        this.notificacoesService = notificacoesService;
    }

    @Scheduled(cron = "0 0 8,13,22 * * *")
    public void verificarTreinosVencendo(){
        logger.info("Executando verificarTreinosVencendo em {}", LocalDateTime.now());
        notificacoesService.enviarNotificacoesTreinosVencendo();
        notificacoesService.notificarPersonaisTreinadores();
    }
}
