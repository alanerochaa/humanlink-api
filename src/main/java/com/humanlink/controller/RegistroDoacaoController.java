package com.humanlink.controller;

import com.humanlink.DTOs.RegistroDoacaoDTO;
import com.humanlink.service.RegistroDoacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/registro-doacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Registro Doação", description = "CRUD para registros de doação")
public class RegistroDoacaoController {

    @Inject
    RegistroDoacaoService service;

    @GET
    @Operation(summary = "Lista todas as doações")
    @APIResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<RegistroDoacaoDTO> listarTodos() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca doação por ID")
    @APIResponse(responseCode = "200", description = "Doação encontrada")
    @APIResponse(responseCode = "404", description = "Doação não encontrada")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            RegistroDoacaoDTO dto = service.findById(id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Operation(summary = "Cria um novo registro de doação")
    @APIResponse(responseCode = "201", description = "Doação criada com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    public Response criar(@Valid @RequestBody(description = "Dados da doação", required = true) RegistroDoacaoDTO dto) {
        RegistroDoacaoDTO criado = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(criado).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza um registro de doação")
    @APIResponse(responseCode = "200", description = "Doação atualizada com sucesso")
    @APIResponse(responseCode = "404", description = "Doação não encontrada")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    public Response atualizar(@PathParam("id") Integer id, @Valid @RequestBody(description = "Dados atualizados da doação", required = true) RegistroDoacaoDTO dto) {
        try {
            RegistroDoacaoDTO atualizado = service.update(id, dto);
            return Response.ok(atualizado).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Exclui um registro de doação")
    @APIResponse(responseCode = "204", description = "Doação excluída com sucesso")
    @APIResponse(responseCode = "404", description = "Doação não encontrada")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            service.delete(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
