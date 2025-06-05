package com.humanlink.exception;

import com.humanlink.exception.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ResponseDTO.builder()
                        .mensagem("An unexpected error occurred")
                        .dataErro(LocalDateTime.now())
                        .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .build())
                .build();
    }
}
