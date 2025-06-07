package com.humanlink.controller;

import com.humanlink.DTOs.LocalizacaoRegistradaDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.service.LocalizacaoRegistradaService;
import com.humanlink.util.ValidatorUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "apiKey")
@Path("/localizacoes-registradas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Localização Registrada", description = "Operações relacionadas às localizações registradas")
public class LocalizacaoRegistradaController {

    @Inject
    LocalizacaoRegistradaService service;

    @GET
    @Operation(summary = "Listar todas as localizações registradas")
    @APIResponse(responseCode = "200", description = "Lista de localizações registradas retornada")
    public List<LocalizacaoRegistradaDTO> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar localização registrada por ID")
    @APIResponse(responseCode = "200", description = "Localização registrada encontrada")
    @APIResponse(responseCode = "404", description = "Localização registrada não encontrada")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            LocalizacaoRegistradaDTO dto = service.buscarPorId(id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Operation(summary = "Criar uma nova localização registrada")
    @APIResponse(responseCode = "201", description = "Localização registrada criada")
    public Response criar(@Valid LocalizacaoRegistradaDTO dto) {
        ValidatorUtils.validate(dto);
        LocalizacaoRegistradaDTO criada = service.criar(dto);
        return Response.status(Response.Status.CREATED).entity(criada).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar localização registrada por ID")
    @APIResponse(responseCode = "200", description = "Localização registrada atualizada")
    @APIResponse(responseCode = "404", description = "Localização registrada não encontrada")
    public Response atualizar(@PathParam("id") Integer id, @Valid LocalizacaoRegistradaDTO dto) {
        try {
            LocalizacaoRegistradaDTO atualizada = service.atualizar(id, dto);
            return Response.ok(atualizada).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar localização registrada por ID")
    @APIResponse(responseCode = "204", description = "Localização registrada deletada")
    @APIResponse(responseCode = "404", description = "Localização registrada não encontrada")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            service.deletar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
