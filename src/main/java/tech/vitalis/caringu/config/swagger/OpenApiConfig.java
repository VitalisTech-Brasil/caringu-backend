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
                title = "Projeto CaringU",
                description = "Documentação Swagger - API CaringU ",
                contact = @Contact(
                        name = "Vitalis Tech",
                        url = "https://github.com/VitalisTech-Brasil/caringu-backend",
                        email = "vitalistech06@gmail.com"
                ),
                license = @License(name = "UNLICENSED"),
                version = "1.0.0"
        ),
        tags = {
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Usuários", description = "Endpoints de usuários"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Autenticação", description = "Login, registro e JWT"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Alunos", description = "Operações com alunos"),
                @io.swagger.v3.oas.annotations.tags.Tag(name = "Personais", description = "Operações com personal trainers")
        }
)
@SecurityScheme(
        name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"
)

public class OpenApiConfig {
}
