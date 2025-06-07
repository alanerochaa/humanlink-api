package com.humanlink.security;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@ApplicationPath("/humanlink") // coloque aqui seu path base, ajuste se necessário
@OpenAPIDefinition(
        info = @Info(
                title = "HumanLink API",
                version = "1.0.0",
                description = "Documentação da API HumanLink"
        ),
        security = {
                @SecurityRequirement(name = "apiKey") // <-- ativa o cadeado no Swagger
        },
        servers = {
                @Server(url = "/humanlink", description = "Servidor padrão da API")
        }
)
public class SwaggerApplication extends Application {
}
