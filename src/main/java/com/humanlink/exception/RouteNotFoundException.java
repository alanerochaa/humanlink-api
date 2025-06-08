package com.humanlink.exception;

import com.humanlink.exception.dto.ResponseDTO;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.time.LocalDateTime;

public class RouteNotFoundException implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ResponseDTO.builder()
                        .mensagem("Route not found")
                        .status(Response.Status.NOT_FOUND.getStatusCode())
                        .dataErro(LocalDateTime.now())
                        .build())
                .build();
    }
}
