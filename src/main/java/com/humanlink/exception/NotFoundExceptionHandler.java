package com.humanlink.exception;

import com.humanlink.exception.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.time.LocalDateTime;


public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ResponseDTO.builder()
                        .mensagem(exception.getMessage())
                        .dataErro(LocalDateTime.now())
                        .status(Response.Status.NOT_FOUND.getStatusCode())
                        .build())
                .build();
    }
}
