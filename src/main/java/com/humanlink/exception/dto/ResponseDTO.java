package com.humanlink.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    @JsonProperty("mensagem")
    private String mensagem;

    @JsonProperty("erros")
    private List<String> erros;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("data_erro")
    private LocalDateTime dataErro;
}
