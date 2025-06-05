package com.humanlink.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    @Schema(example = "1")
    @JsonProperty("id_login")
    private Integer idLogin;

    @NotNull
    @Email
    @Size(max = 255)
    @Schema(example = "ana.silva@example.com")
    @JsonProperty("email")
    private String email;

    @NotNull
    @Size(min = 6, max = 255)
    @Schema(example = "senhaAna123")
    @JsonProperty(value = "senha", access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Size(max = 50)
    @Schema(example = "ATIVO")
    @JsonProperty("status")
    private String status;

    @NotNull
    @Schema(description = "ID do usuário associado ao login")
    @JsonProperty("usuario_id")
    private Integer usuarioId;

    @Schema(example = "2024-05-28T15:30:00")
    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    @Schema(example = "2024-05-28T15:30:00")
    @JsonProperty("data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
