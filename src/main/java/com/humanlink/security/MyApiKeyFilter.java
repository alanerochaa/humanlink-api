package com.humanlink.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyApiKeyFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        // libera swagger e api-docs sem autenticação
        if (path.startsWith("swagger") || path.startsWith("api-docs")) {
            return;
        }

        // Se quiser, aqui você pode validar a API Key para as outras rotas
        // Exemplo: pegar header X-API-Key e validar

        // Por enquanto, libera tudo
        System.out.println("Filtro ativo, liberando rota: " + path);
    }
}
