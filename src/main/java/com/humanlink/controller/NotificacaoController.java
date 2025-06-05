package com.humanlink.controller;

import com.humanlink.DTOs.NotificacaoDTO;
import com.humanlink.model.Notificacao;
import com.humanlink.service.NotificacaoService;
import com.humanlink.util.ValidatorUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/notificacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Notificação", description = "Operações relacionadas à notificação")
public class NotificacaoController {

    @Inject
    NotificacaoService notificacaoService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar notificação por ID")
    @APIResponse(responseCode = "200", description = "Notificação encontrada")
    @APIResponse(responseCode = "404", description = "Notificação não encontrada")
    public Response buscar(@RestPath Integer id) {
        try {
            var notificacao = notificacaoService.buscarNotificacaoPeloId(id);
            return Response.ok(map(notificacao)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Operation(summary = "Listar todas as notificações")
    @APIResponse(responseCode = "200", description = "Lista de notificações retornada")
    public List<NotificacaoDTO> listar() {
        return notificacaoService.listarNotificacoes()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @POST
    @Operation(summary = "Cadastrar nova notificação")
    @APIResponse(responseCode = "201", description = "Notificação criada com sucesso")
    public Response cadastrar(@Valid NotificacaoDTO dto) {
        ValidatorUtils.validate(dto);
        Notificacao nova = notificacaoService.cadastrarNotificacao(dto);
        return Response.status(Response.Status.CREATED).entity(map(nova)).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar notificação")
    @APIResponse(responseCode = "200", description = "Notificação atualizada")
    @APIResponse(responseCode = "404", description = "Notificação não encontrada")
    public Response atualizar(@RestPath Integer id, @Valid NotificacaoDTO dto) {
        ValidatorUtils.validate(dto);
        try {
            Notificacao atualizada = notificacaoService.alterarNotificacao(id, dto);
            return Response.ok(map(atualizada)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir notificação")
    @APIResponse(responseCode = "204", description = "Notificação excluída com sucesso")
    @APIResponse(responseCode = "404", description = "Notificação não encontrada")
    public Response deletar(@RestPath Integer id) {
        try {
            notificacaoService.deletarNotificacao(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    private NotificacaoDTO map(Notificacao n) {
        if (Objects.isNull(n)) {
            return null;
        }
        return NotificacaoDTO.builder()
                .id(n.getId())
                .mensagem(n.getMensagem())
                .canalEnvio(n.getCanalEnvio())
                .dataEnvio(n.getDataEnvio())
                .status(n.getStatus())
                .idUsuario(n.getUsuario() != null ? n.getUsuario().getId() : null)
                .build();
    }
}
