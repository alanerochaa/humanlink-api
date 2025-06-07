package com.humanlink.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyAuthFilter implements ContainerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-key";
    private static final String VALID_API_KEY = "chave123";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Permitir OPTIONS (preflight CORS) sem autenticação
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            return;
        }

        String apiKey = requestContext.getHeaderString(API_KEY_HEADER);

        if (apiKey == null || !apiKey.equals(VALID_API_KEY)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("API key inválida ou ausente")
                            .build()
            );
        }
    }
}
