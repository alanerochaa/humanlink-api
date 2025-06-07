package com.humanlink.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(title = "HumanLink API", version = "1.0"),
        security = @SecurityRequirement(name = "apiKey")
)
@SecurityScheme(
        securitySchemeName = "apiKey",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        apiKeyName = "X-API-Key",
        description = "API Key necessária para autenticação"
)
public class OpenApiConfig {

}
