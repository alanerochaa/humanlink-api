package com.humanlink.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampanhaHumanitariaDTO {

    @Schema(example = "1")
    private Integer id;

    @NotNull
    @Size(max = 4000)
    @JsonProperty("descricao")
    @Schema(example = "Campanha de arrecadação para vítimas do terremoto")
    private String descricao;

    @Size(max = 50)
    @JsonProperty("status_campanha")
    @Schema(example = "ATIVA")
    private String statusCampanha;

    @Size(max = 4000)
    @JsonProperty("publico_alvo")
    @Schema(example = "Famílias afetadas pelo terremoto")
    private String publicoAlvo;

    @Size(max = 100)
    @JsonProperty("tipo_campanha")
    @Schema(example = "Arrecadação")
    private String tipoCampanha;

    @Size(max = 100)
    @JsonProperty("responsavel")
    @Schema(example = "Carlos Pereira")
    private String responsavel;

    @JsonProperty("data_inicio")
    @Schema(example = "2025-05-02")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    @Schema(example = "2025-06-02")
    private LocalDate dataFim;

    @JsonProperty("id_usuario")
    @Schema(example = "2")
    private Integer idUsuario;
}
