package tech.vitalis.caringu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "tech.vitalis.caringu.repository")
public class CaringuApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaringuApplication.class, args);


	}
}
