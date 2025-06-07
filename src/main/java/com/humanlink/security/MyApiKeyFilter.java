package com.humanlink.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyApiKeyFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "api.key")
    String apiKey;

    @ConfigProperty(name = "api.auth.enabled", defaultValue = "true")
    boolean apiAuthEnabled;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Se a autenticação estiver desativada, ignora a verificação
        if (!apiAuthEnabled) {
            return;
        }

        String apiKeyRequest = requestContext.getHeaderString("X-API-key");

        if (apiKeyRequest == null || !apiKeyRequest.equals(apiKey)) {
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response
                            .status(jakarta.ws.rs.core.Response.Status.FORBIDDEN)
                            .entity("API Key inválida ou ausente.")
                            .build()
            );
        }
    }
}
