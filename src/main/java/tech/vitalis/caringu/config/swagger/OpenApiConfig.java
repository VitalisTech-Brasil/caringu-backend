package tech.vitalis.caringu.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API RESTful CaringU",
                description = "Documentação da API do projeto CaringU, desenvolvida pela Vitalis Tech para gestão de treinos e acompanhamento de alunosId.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Vitalis Tech",
                        url = "https://github.com/VitalisTech-Brasil/caringu-backend",
                        email = "vitalistech06@gmail.com"
                ),
                license = @License(name = "Proprietary - All rights reserved")
        )
)
@SecurityScheme(
        name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"
)

public class OpenApiConfig {
}
