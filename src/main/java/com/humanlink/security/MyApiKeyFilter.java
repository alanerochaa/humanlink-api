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
public class MyApiKeyFilter implements ContainerRequestFilter {

    // Injeta a chave de API diretamente do application.properties
    @ConfigProperty(name = "api.key")
    String apiKey;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        // Liberar o Swagger/OpenAPI e rota /humanlink sem API key
        if (path.equals("humanlink") ||
                path.startsWith("humanlink/swagger-ui") ||
                path.startsWith("humanlink/openapi")) {
            return; // permite acesso sem validar API key
        }

        String apiKeyRequest = requestContext.getHeaderString("X-API-key");
        if (apiKeyRequest == null || !apiKeyRequest.equals(apiKey)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("API key is missing or invalid")
                            .build()
            );
        }
    }
}
