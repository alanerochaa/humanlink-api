package com.humanlink.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {

    @Schema(example = "1")
    @JsonProperty("id_usuario")
    private Integer idUsuario;

    @NotNull
    @Size(max = 100)
    @Schema(example = "Ana Silva")
    @JsonProperty("nome")
    private String nome;

    @NotNull
    @Size(max = 20)
    @Schema(example = "123.456.789-00")
    @JsonProperty("cpf")
    private String cpf;

    @NotNull
    @Size(max = 255)
    @Schema(example = "ana.silva@example.com")
    @JsonProperty("email")
    private String email;

    @Size(max = 20)
    @Schema(example = "(11) 99999-1111")
    @JsonProperty("telefone")
    private String telefone;

    @Size(max = 20)
    @Schema(example = "VITIMA")
    @JsonProperty("tipo_usuario")
    private String tipoUsuario;

    @Schema(example = "2024-05-28")
    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    @Schema(example = "2024-05-28")
    @JsonProperty("data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
