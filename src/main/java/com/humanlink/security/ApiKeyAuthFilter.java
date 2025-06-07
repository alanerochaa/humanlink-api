package com.humanlink.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyAuthFilter implements ContainerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-key";

    @ConfigProperty(name = "api.key")
    String validApiKey;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Permitir requisições OPTIONS para suportar CORS sem autenticação
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            return;
        }

        String apiKey = requestContext.getHeaderString(API_KEY_HEADER);

        if (apiKey == null || !apiKey.equals(validApiKey)) {
            // Responde com 401 Unauthorized e mensagem de erro
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("API key inválida ou ausente")
                            .build()
            );
        }
    }
}
