package com.humanlink.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocalizacaoRegistradaDTO {

    @Schema(example = "1")
    private Integer id;

    @NotNull
    @Size(max = 100)
    @JsonProperty("tipo_local")
    @Schema(example = "Abrigo temporário")
    private String tipoLocal;

    @NotNull
    @Size(max = 50)
    @JsonProperty("risco_area")
    @Schema(example = "Alto")
    private String riscoArea;

    @NotNull
    @JsonProperty("latitude")
    @Schema(example = "-23.550520")
    private BigDecimal latitude;

    @NotNull
    @JsonProperty("longitude")
    @Schema(example = "-46.633308")
    private BigDecimal longitude;

    @Size(max = 4000)
    @JsonProperty("endereco")
    @Schema(example = "Rua das Flores, 123 - Bairro Esperança")
    private String endereco;

    @JsonProperty("data_registro")
    @Schema(example = "2025-06-02T14:30:00")
    private LocalDateTime dataRegistro;

    @JsonProperty("id_usuario")
    @Schema(example = "2")
    private Integer idUsuario;
}
