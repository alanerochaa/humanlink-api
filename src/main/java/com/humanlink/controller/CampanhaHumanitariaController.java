package com.humanlink.controller;

import com.humanlink.DTOs.CampanhaHumanitariaDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.service.CampanhaHumanitariaService;
import com.humanlink.util.ValidatorUtils;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Logger;

@Path("/campanhas-humanitarias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Campanha Humanitária", description = "Operações relacionadas às campanhas humanitárias")
public class CampanhaHumanitariaController {

    private static final Logger LOGGER = Logger.getLogger(CampanhaHumanitariaController.class.getName());

    @Inject
    CampanhaHumanitariaService service;

    @GET
    @Operation(summary = "Listar todas as campanhas humanitárias")
    @APIResponse(responseCode = "200", description = "Lista de campanhas humanitárias retornada",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CampanhaHumanitariaDTO.class, type = SchemaType.ARRAY)))
    public Response listarTodos() {
        LOGGER.info("Listando todas campanhas humanitárias");
        List<CampanhaHumanitariaDTO> campanhas = service.listarTodos();
        return Response.ok(campanhas).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar campanha humanitária por ID")
    @APIResponse(responseCode = "200", description = "Campanha humanitária encontrada",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CampanhaHumanitariaDTO.class)))
    @APIResponse(responseCode = "404", description = "Campanha humanitária não encontrada")
    public Response buscarPorId(@PathParam("id") Integer id) {
        LOGGER.info("Buscando campanha humanitária com ID: " + id);
        try {
            CampanhaHumanitariaDTO dto = service.buscarPorId(id);
            return Response.ok(dto).build();
        } catch (NotFoundException e) {
            LOGGER.warning(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    @POST
    @Operation(summary = "Criar uma nova campanha humanitária")
    @APIResponse(responseCode = "201", description = "Campanha humanitária criada",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CampanhaHumanitariaDTO.class)))
    @APIResponse(responseCode = "400", description = "Dados inválidos enviados")
    @Transactional
    public Response criar(@Valid CampanhaHumanitariaDTO dto) {
        LOGGER.info("Criando campanha humanitária");
        ValidatorUtils.validate(dto);
        CampanhaHumanitariaDTO campanhaCriada = service.criarCampanha(dto);
        return Response.status(Response.Status.CREATED).entity(campanhaCriada).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar campanha humanitária por ID")
    @APIResponse(responseCode = "200", description = "Campanha humanitária atualizada",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CampanhaHumanitariaDTO.class)))
    @APIResponse(responseCode = "404", description = "Campanha humanitária não encontrada")
    @APIResponse(responseCode = "400", description = "Dados inválidos enviados")
    public Response atualizar(@PathParam("id") Integer id, @Valid CampanhaHumanitariaDTO dto) {
        LOGGER.info("Atualizando campanha humanitária com ID: " + id);
        try {
            CampanhaHumanitariaDTO atualizada = service.atualizar(id, dto);
            return Response.ok(atualizada).build();
        } catch (NotFoundException e) {
            LOGGER.warning(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar campanha humanitária por ID")
    @APIResponse(responseCode = "204", description = "Campanha humanitária deletada")
    @APIResponse(responseCode = "404", description = "Campanha humanitária não encontrada")
    public Response deletar(@PathParam("id") Integer id) {
        LOGGER.info("Deletando campanha humanitária com ID: " + id);
        try {
            service.deletar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOGGER.warning(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
        }
    }

    // Classe simples para retornar mensagens de erro JSON padronizadas
    private static class ErrorMessage {
        public String mensagem;
        public ErrorMessage(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}
