package com.humanlink.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "AREA_DESASTRE")
@AllArgsConstructor
@NoArgsConstructor
public class AreaDesastre extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_desastre_seq")
    @SequenceGenerator(name = "area_desastre_seq", sequenceName = "seq_area_desastre", allocationSize = 1)
    @Column(name = "id_desastre", nullable = false)
    private Integer idDesastre;

    @Column(name = "tipo_desastre", length = 100, nullable = false)
    private String tipoDesastre;

    @Column(name = "descricao", length = 4000)
    private String descricao;

    @Column(name = "prioridade_resposta", length = 50, nullable = false)
    private String prioridadeResposta;

    @Column(name = "status_emergencia", length = 50, nullable = false)
    private String statusEmergencia;

    @Column(name = "latitude", nullable = false)
    private BigDecimal  latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;
}
