package com.humanlink.controller;

import com.humanlink.DTOs.AreaDesastreDTO;
import com.humanlink.model.AreaDesastre;
import com.humanlink.service.AreaDesastreService;
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
import java.util.stream.Collectors;

@Path("/areas-desastre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Área de Desastre", description = "Operações relacionadas à área de desastre")
public class AreaDesastreController {

    @Inject
    AreaDesastreService service;

    @GET
    @Operation(summary = "Listar todas as áreas de desastre")
    @APIResponse(responseCode = "200", description = "Lista de áreas de desastre retornada")
    public List<AreaDesastreDTO> listarTodos() {
        return service.listarTodos().stream()
                .map(AreaDesastreDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar área de desastre por ID")
    @APIResponse(responseCode = "200", description = "Área de desastre encontrada")
    @APIResponse(responseCode = "404", description = "Área de desastre não encontrada")
    public Response buscarPorId(@PathParam("id") Integer id) {
        return service.buscarPorId(id)
                .map(AreaDesastreDTO::fromEntity)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Operation(summary = "Criar nova área de desastre")
    @APIResponse(responseCode = "201", description = "Área de desastre criada com sucesso")
    public Response criar(@Valid AreaDesastreDTO dto) {
        try {
            ValidatorUtils.validate(dto);
            AreaDesastre nova = service.criar(dto.toEntity());
            return Response.status(Response.Status.CREATED)
                    .entity(AreaDesastreDTO.fromEntity(nova))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro interno ao criar área de desastre: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar área de desastre existente")
    @APIResponse(responseCode = "200", description = "Área de desastre atualizada")
    @APIResponse(responseCode = "404", description = "Área de desastre não encontrada")
    public Response atualizar(@PathParam("id") Integer id, @Valid AreaDesastreDTO dtoAtualizada) {
        try {
            ValidatorUtils.validate(dtoAtualizada);
            AreaDesastre atualizada = service.atualizar(id, dtoAtualizada.toEntity());
            return Response.ok(AreaDesastreDTO.fromEntity(atualizada)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"Erro ao atualizar: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar área de desastre por ID")
    @APIResponse(responseCode = "204", description = "Área de desastre removida com sucesso")
    @APIResponse(responseCode = "404", description = "Área de desastre não encontrada")
    public Response deletar(@PathParam("id") Integer id) {
        boolean excluido = service.deletar(id);
        if (!excluido) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"Área de desastre não encontrada\"}")
                    .build();
        }
        return Response.noContent().build();
    }
}
