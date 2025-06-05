package com.humanlink.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRO_VOLUNTARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RegistroVoluntario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registro_voluntario_seq")
    @SequenceGenerator(name = "registro_voluntario_seq", sequenceName = "seq_registro_voluntario", allocationSize = 1)
    @Column(name = "id_voluntario")
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "tipo_ajuda", length = 100)
    private String tipo_ajuda;

    @Column(name = "disponibilidade", length = 50)
    private String disponibilidade;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
