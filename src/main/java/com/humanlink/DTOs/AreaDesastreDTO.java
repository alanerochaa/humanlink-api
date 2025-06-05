package com.humanlink.DTOs;

import com.humanlink.model.AreaDesastre;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaDesastreDTO {

    private Integer idDesastre;

    @NotBlank(message = "Tipo de desastre é obrigatório")
    @Size(max = 100, message = "Tipo de desastre deve ter no máximo 100 caracteres")
    private String tipoDesastre;

    @Size(max = 4000, message = "Descrição deve ter no máximo 4000 caracteres")
    private String descricao;

    @NotBlank(message = "Prioridade de resposta é obrigatória")
    @Size(max = 50, message = "Prioridade de resposta deve ter no máximo 50 caracteres")
    private String prioridadeResposta;

    @NotBlank(message = "Status de emergência é obrigatório")
    @Size(max = 50, message = "Status de emergência deve ter no máximo 50 caracteres")
    private String statusEmergencia;

    @NotNull(message = "Latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "Latitude mínima é -90.0")
    @DecimalMax(value = "90.0", message = "Latitude máxima é 90.0")
    private BigDecimal latitude;

    @NotNull(message = "Longitude é obrigatória")
    @DecimalMin(value = "-180.0", message = "Longitude mínima é -180.0")
    @DecimalMax(value = "180.0", message = "Longitude máxima é 180.0")
    private BigDecimal longitude;

    @NotNull(message = "Data de ocorrência é obrigatória")
    private LocalDateTime dataOcorrencia;

    public static AreaDesastreDTO fromEntity(AreaDesastre entity) {
        if (entity == null) return null;
        return AreaDesastreDTO.builder()
                .idDesastre(entity.getIdDesastre())
                .tipoDesastre(entity.getTipoDesastre())
                .descricao(entity.getDescricao())
                .prioridadeResposta(entity.getPrioridadeResposta())
                .statusEmergencia(entity.getStatusEmergencia())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .dataOcorrencia(entity.getDataOcorrencia())
                .build();
    }


    public AreaDesastre toEntity() {
        AreaDesastre entity = new AreaDesastre();
        entity.setIdDesastre(this.idDesastre);
        entity.setTipoDesastre(this.tipoDesastre);
        entity.setDescricao(this.descricao);
        entity.setPrioridadeResposta(this.prioridadeResposta);
        entity.setStatusEmergencia(this.statusEmergencia);
        entity.setLatitude(this.latitude);
        entity.setLongitude(this.longitude);
        entity.setDataOcorrencia(this.dataOcorrencia);
        return entity;
    }


}
