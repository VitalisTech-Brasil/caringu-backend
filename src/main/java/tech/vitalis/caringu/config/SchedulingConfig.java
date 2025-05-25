package tech.vitalis.caringu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tech.vitalis.caringu.service.NotificacaoTreinoVencimentoService;
import tech.vitalis.caringu.service.NotificacoesService;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    private final NotificacaoTreinoVencimentoService notificacoesService;

    public SchedulingConfig(NotificacaoTreinoVencimentoService notificacoesService) {
        this.notificacoesService = notificacoesService;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void verificarTreinosVencendo(){
        notificacoesService.enviarNotificacoesTreinosVencendo();
        notificacoesService.notificarPersonaisTreinadores();
    }
}
