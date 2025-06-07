package com.humanlink.controller;

import com.humanlink.DTOs.UsuarioDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;


@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Usuario", description = "Operações relacionadas a usuários")
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Operation(summary = "Listar usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @APIResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @GET
    @Path("/{idUsuario}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário pelo seu ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuário encontrado"),
            @APIResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UsuarioDTO buscarPorId(@PathParam("idUsuario") Integer idUsuario) {
        var usuario = usuarioService.buscarPorId(idUsuario);
        if (Objects.isNull(usuario)) {
            throw new NotFoundException("Usuário não encontrado para o ID: " + idUsuario);
        }
        return usuario;
    }

    @POST
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário")
    @APIResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public Response criarUsuario(UsuarioDTO usuarioDTO) {
        com.humanlink.util.ValidatorUtils.validate(usuarioDTO);
        UsuarioDTO criado = usuarioService.criarUsuario(usuarioDTO);
        return Response.status(Response.Status.CREATED).entity(criado).build();
    }

    @PUT
    @Path("/{idUsuario}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @APIResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UsuarioDTO atualizarUsuario(@PathParam("idUsuario") Integer idUsuario, UsuarioDTO usuarioDTO) {
        com.humanlink.util.ValidatorUtils.validate(usuarioDTO);
        var atualizado = usuarioService.atualizar(idUsuario, usuarioDTO);
        if (Objects.isNull(atualizado)) {
            throw new NotFoundException("Usuário não encontrado para o ID: " + idUsuario);
        }
        return atualizado;
    }

    @DELETE
    @Path("/{idUsuario}")
    @Operation(summary = "Remover usuário", description = "Remove um usuário pelo seu ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @APIResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public Response deletarUsuario(@PathParam("idUsuario") Integer idUsuario) {
        usuarioService.deletar(idUsuario);
        return Response.noContent().build();
    }
}
