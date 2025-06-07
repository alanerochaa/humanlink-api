package com.humanlink.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "CAMPANHA_HUMANITARIA")
@AllArgsConstructor
@NoArgsConstructor
public class CampanhaHumanitaria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campanha", nullable = false)
    private Integer idCampanha;

    @Column(name = "descricao", length = 4000, nullable = false)
    private String descricao;

    @Column(name = "status_campanha", length = 50)
    private String statusCampanha;

    @Column(name = "publico_alvo", length = 4000)
    private String publicoAlvo;

    @Column(name = "tipo_campanha", length = 100)
    private String tipoCampanha;

    @Column(name = "responsavel", length = 100)
    private String responsavel;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
