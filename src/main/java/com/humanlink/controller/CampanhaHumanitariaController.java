package com.humanlink.controller;

import com.humanlink.DTOs.CampanhaHumanitariaDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.service.CampanhaHumanitariaService;
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

@Path("/campanhas-humanitarias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Campanha Humanitária", description = "Operações relacionadas às campanhas humanitárias")
public class CampanhaHumanitariaController {

    @Inject
    CampanhaHumanitariaService service;

    @GET
    @Operation(summary = "Listar todas as campanhas humanitárias")
    @APIResponse(responseCode = "200", description = "Lista de campanhas humanitárias retornada")
    public List<CampanhaHumanitariaDTO> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar campanha humanitária por ID")
    @APIResponse(responseCode = "200", description = "Campanha humanitária encontrada")
    @APIResponse(responseCode = "404", description = "Campanha humanitária não encontrada")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            CampanhaHumanitariaDTO dto = service.buscarPorId(id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Operation(summary = "Criar uma nova campanha humanitária")
    @APIResponse(responseCode = "201", description = "Campanha humanitária criada")
    public Response criar(@Valid CampanhaHumanitariaDTO dto) {
        ValidatorUtils.validate(dto);
        CampanhaHumanitariaDTO campanhaCriada = service.criarCampanha(dto);
        return Response.status(Response.Status.CREATED)
                .entity(campanhaCriada)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar campanha humanitária por ID")
    @APIResponse(responseCode = "200", description = "Campanha humanitária atualizada")
    @APIResponse(responseCode = "404", description = "Campanha humanitária não encontrada")
    public Response atualizar(@PathParam("id") Integer id, @Valid CampanhaHumanitariaDTO dto) {
        try {
            CampanhaHumanitariaDTO atualizada = service.atualizar(id, dto);
            return Response.ok(atualizada).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar campanha humanitária por ID")
    @APIResponse(responseCode = "204", description = "Campanha humanitária deletada")
    @APIResponse(responseCode = "404", description = "Campanha humanitária não encontrada")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            service.deletar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
