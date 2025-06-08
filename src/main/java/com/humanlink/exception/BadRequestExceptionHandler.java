package com.humanlink.exception;

import com.humanlink.exception.dto.ResponseDTO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.time.LocalDateTime;


public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .mensagem(exception.getMessage())
                .dataErro(LocalDateTime.now())
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(responseDTO)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
