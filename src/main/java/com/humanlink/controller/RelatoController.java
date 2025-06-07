package com.humanlink.controller;

import com.humanlink.DTOs.RelatoDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.service.RelatoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "apiKey")
@Path("/relatos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Relatos", description = "CRUD de relatos de desastres")
public class RelatoController {

    @Inject
    RelatoService relatoService;

    @GET
    @Operation(summary = "Listar todos os relatos")
    @APIResponse(responseCode = "200", description = "Lista de relatos retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RelatoDTO.class, type = SchemaType.ARRAY)))
    public Response listarTodos() {
        List<RelatoDTO> relatos = relatoService.listarTodos();
        return Response.ok(relatos).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar relato por ID")
    @APIResponse(responseCode = "200", description = "Relato encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RelatoDTO.class)))
    @APIResponse(responseCode = "404", description = "Relato não encontrado")
    public Response buscarPorId(@PathParam("id") Integer id) {
        return relatoService.buscarPorId(id)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .orElseThrow(() -> new NotFoundException("Relato com ID " + id + " não encontrado."));
    }

    @POST
    @Operation(summary = "Criar novo relato")
    @APIResponse(responseCode = "201", description = "Relato criado com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    public Response criar(@Valid RelatoDTO dto) {
        Integer idCriado = relatoService.criarRelato(dto);
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Relato criado com sucesso\",\"id\":" + idCriado + "}")
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar relato existente")
    @APIResponse(responseCode = "200", description = "Relato atualizado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RelatoDTO.class)))
    @APIResponse(responseCode = "404", description = "Relato não encontrado")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    public Response atualizar(@PathParam("id") Integer id, @Valid RelatoDTO dto) {
        RelatoDTO atualizado = relatoService.atualizar(id, dto);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar relato por ID")
    @APIResponse(responseCode = "204", description = "Relato deletado com sucesso")
    @APIResponse(responseCode = "404", description = "Relato não encontrado")
    public Response deletar(@PathParam("id") Integer id) {
        relatoService.deletar(id);
        return Response.noContent().build();
    }
}
