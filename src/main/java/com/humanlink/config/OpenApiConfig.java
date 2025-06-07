package com.humanlink.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@ApplicationScoped
@OpenAPIDefinition(
        info = @Info(title = "HumanLink API", version = "1.0"),
        security = @SecurityRequirement(name = "apiKey")
)
@SecurityScheme(
        securitySchemeName = "apiKey",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        apiKeyName = "X-API-key",  // mantém o padrão do header em lowercase no "key"
        description = "API Key necessária para autenticação"
)
public class OpenApiConfig {

}
