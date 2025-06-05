package com.humanlink.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "LOCALIZACAO_REGISTRADA")
@AllArgsConstructor
@NoArgsConstructor
public class LocalizacaoRegistrada extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "localizacao_seq",
            sequenceName = "seq_localizacao_registrada",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localizacao_seq")
    @Column(name = "id_localizacao", nullable = false)
    private Integer idLocalizacao;

    @Column(name = "tipo_local", length = 100, nullable = false)
    private String tipoLocal;

    @Column(name = "risco_area", length = 50, nullable = false)
    private String riscoArea;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    @Column(name = "endereco", length = 4000)
    private String endereco;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_localizacao_usuario"))
    private Usuario usuario;
}
