package com.humanlink.controller;

import com.humanlink.DTOs.LoginDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "apiKey")
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Login", description = "Operações relacionadas a login")
public class LoginController {

    @Inject
    LoginService loginService;

    @GET
    @Operation(summary = "Listar logins", description = "Retorna uma lista de todos os logins cadastrados")
    @APIResponse(responseCode = "200", description = "Lista de logins retornada com sucesso")
    public List<LoginDTO> listarLogins() {
        return loginService.listarTodos();
    }

    @GET
    @Path("/{idLogin}")
    @Operation(summary = "Buscar login por ID", description = "Retorna um login pelo seu ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Login encontrado"),
            @APIResponse(responseCode = "404", description = "Login não encontrado")
    })
    public Response buscarPorId(@PathParam("idLogin") Integer idLogin) {
        try {
            LoginDTO dto = loginService.buscarPorId(idLogin);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Operation(summary = "Criar novo login", description = "Cria um novo login")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Login criado com sucesso"),
            @APIResponse(responseCode = "400", description = "Dados inválidos para criação"),
            @APIResponse(responseCode = "404", description = "Usuário associado não encontrado")
    })
    public Response criarLogin(LoginDTO loginDTO) {
        try {
            com.humanlink.util.ValidatorUtils.validate(loginDTO);
            LoginDTO criado = loginService.criarLogin(loginDTO);
            return Response.created(URI.create("/login/" + criado.getIdLogin()))
                    .entity(criado)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{idLogin}")
    @Operation(summary = "Atualizar login", description = "Atualiza os dados de um login existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Login atualizado com sucesso"),
            @APIResponse(responseCode = "404", description = "Login não encontrado"),
            @APIResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    public Response atualizarLogin(@PathParam("idLogin") Integer idLogin, LoginDTO loginDTO) {
        try {
            com.humanlink.util.ValidatorUtils.validate(loginDTO);
            LoginDTO atualizado = loginService.atualizar(idLogin, loginDTO);
            return Response.ok(atualizado).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{idLogin}")
    @Operation(summary = "Excluir login", description = "Remove um login pelo seu ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Login excluído com sucesso"),
            @APIResponse(responseCode = "404", description = "Login não encontrado")
    })
    public Response deletarLogin(@PathParam("idLogin") Integer idLogin) {
        try {
            loginService.deletar(idLogin);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
