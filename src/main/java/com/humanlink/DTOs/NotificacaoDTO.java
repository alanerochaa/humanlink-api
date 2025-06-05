package com.humanlink.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificacaoDTO {

    @Schema(example = "1")
    private Integer id;

    @NotNull
    @Size(max = 4000)
    @JsonProperty("mensagem")
    @Schema(example = "Alerta de enchente na região norte.")
    private String mensagem;

    @Size(max = 100)
    @JsonProperty("canal_envio")
    @Schema(example = "Email")
    private String canalEnvio;

    @JsonProperty("data_envio")
    @Schema(example = "2025-05-29T14:30:00")
    private LocalDateTime dataEnvio;

    @Size(max = 50)
    @JsonProperty("status")
    @Schema(example = "ENVIADO")
    private String status;

    @JsonProperty("id_usuario")
    @Schema(example = "123")
    private Integer idUsuario;
}
