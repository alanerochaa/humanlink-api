package com.humanlink.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "NOTIFICACAO")
public class Notificacao extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificacao_seq")
    @SequenceGenerator(name = "notificacao_seq", sequenceName = "seq_notificacao", allocationSize = 1)
    @Column(name = "id_notificacao")
    private Integer id;

    @Column(name = "mensagem", length = 4000, nullable = false)
    private String mensagem;

    @Column(name = "canal_envio", length = 100)
    private String canalEnvio;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "status_notificacao", length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
