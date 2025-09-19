package tech.vitalis.caringu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import tech.vitalis.caringu.service.NotificacaoPlanoVencimentoService;
import tech.vitalis.caringu.service.NotificacaoTreinoVencimentoService;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories(basePackages = "tech.vitalis.caringu.repository")
public class CaringuApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaringuApplication.class, args);
	}
	/*
	// Este bean é executado depois que o Spring inicializa todos os beans
	@Bean
	CommandLineRunner testeNotificacoes(
			NotificacaoPlanoVencimentoService planoService,
			NotificacaoTreinoVencimentoService treinoService) {
		return args -> {
			System.out.println("🔔 Enviando notificações de planos...");
			planoService.enviarNotificacoesPlanoVencimento();
			planoService.notificarPersonais();

			System.out.println("🔔 Enviando notificações de treinos...");
			treinoService.enviarNotificacoesTreinosVencendo();
			treinoService.notificarPersonaisTreinadores();

			System.out.println("✅ Teste finalizado!");
		};
	}

	 */
}
