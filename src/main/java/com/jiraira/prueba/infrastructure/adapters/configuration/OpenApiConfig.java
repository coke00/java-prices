package com.jiraira.prueba.infrastructure.adapters.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API prueba técnica")
                        .description("Proyecto enfocado en realizar pruebas de codificación.")
                        .version("1.0")
                        .termsOfService("https://www.linkedin.com/in/jorge-iraira-ab099476/terms")
                        .contact(new Contact()
                                .name("Jorge Iraira")
                                .url("https://www.linkedin.com/in/jorge-iraira-ab099476/")
                                .email("jorgeiraira55@gmail.com"))
                        .license(new License()
                                .name("License of API")
                                .url("https://www.linkedin.com/in/jorge-iraira-ab099476/license")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server"),
                        new Server().url("https://xx.qa.midominio.com").description("QA server"),
                        new Server().url("https://xx.staging.midominio.com").description("Staging server"),
                        new Server().url("https://xx.produccion.com").description("Production server")
                ))
                .tags(List.of(
                        new Tag().name("Price").description("Operations related to prices")
                ));
    }

}
