package com.humanlink.security;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@SecurityScheme(
        securitySchemeName = "apiKey",
        type = SecuritySchemeType.APIKEY,
        apiKeyName = "X-API-key",
        in = SecuritySchemeIn.HEADER,
        description = "Chave de API para autenticação"
)
public class OpenApiSecurityConfig {
}
