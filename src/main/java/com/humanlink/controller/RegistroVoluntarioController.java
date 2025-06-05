package com.humanlink.controller;

import com.humanlink.DTOs.RegistroVoluntarioDTO;
import com.humanlink.service.RegistroVoluntarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/voluntarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Registro Voluntário", description = "CRUD para registros de voluntariado")
public class RegistroVoluntarioController {

    @Inject
    RegistroVoluntarioService service;

    @GET
    @Operation(summary = "Lista todos os registros de voluntariado", description = "Lista todos os voluntários cadastrados na plataforma")
    public List<RegistroVoluntarioDTO> listarTodos() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca registro de voluntário por ID", description = "Retorna um registro de voluntário específico pelo seu ID")
    public RegistroVoluntarioDTO buscarPorId(@PathParam("id") Integer id) {
        return service.findById(id);
    }

    @POST
    @Operation(summary = "Cria um novo registro de voluntariado", description = "Salva um novo registro de voluntário na plataforma")
    public Response salvar(@Valid RegistroVoluntarioDTO dto) {
        RegistroVoluntarioDTO criado = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(criado).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza um registro de voluntariado existente", description = "Atualiza os dados de um registro de voluntário pelo ID informado")
    public Response atualizar(@PathParam("id") Integer id, @Valid RegistroVoluntarioDTO dto) {
        RegistroVoluntarioDTO atualizado = service.update(id, dto);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Exclui um registro de voluntariado", description = "Deleta um registro de voluntário pelo seu ID")
    public Response deletar(@PathParam("id") Integer id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
