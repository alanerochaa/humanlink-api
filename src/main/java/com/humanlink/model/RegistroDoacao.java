package com.humanlink.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "REGISTRO_DOACAO")
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDoacao extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registro_doacao_seq")
    @SequenceGenerator(name = "registro_doacao_seq", sequenceName = "seq_registro_doacao", allocationSize = 1)
    @Column(name = "id_doacao", nullable = false)
    private Integer idDoacao;

    @Column(name = "tipo_doacao", length = 100, nullable = false)
    private String tipoDoacao;

    @Column(name = "quantidade_doacao", nullable = false)
    private Integer quantidadeDoacao;

    @Column(name = "descricao", length = 4000)
    private String descricao;

    @Column(name = "destino_doacao", length = 4000)
    private String destinoDoacao;

    @Column(name = "data_doacao")
    private LocalDateTime dataDoacao;

    @Column(name = "status", length = 50)
    private String status;

    // Relacionamento com Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "doacao_usuario_fk"))
    private Usuario usuario;

    // Relacionamento com AreaDesastre
    @ManyToOne
    @JoinColumn(name = "area_desastre_id", foreignKey = @ForeignKey(name = "doacao_desastre_fk"))
    private AreaDesastre areaDesastre;

    // Relacionamento com CampanhaHumanitaria
    @ManyToOne
    @JoinColumn(name = "campanha_humanitaria_id", foreignKey = @ForeignKey(name = "doacao_campanha_fk"))
    private CampanhaHumanitaria campanhaHumanitaria;

}
