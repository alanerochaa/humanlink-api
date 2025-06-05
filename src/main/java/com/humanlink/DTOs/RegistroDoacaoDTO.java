package com.humanlink.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroDoacaoDTO {

    private Integer id;

    @NotBlank(message = "Tipo de doação é obrigatório")
    @Size(max = 100, message = "Tipo de doação deve ter no máximo 100 caracteres")
    private String tipoDoacao;

    @NotNull(message = "Quantidade da doação é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidadeDoacao;

    @Size(max = 4000, message = "Descrição deve ter no máximo 4000 caracteres")
    private String descricao;

    @Size(max = 4000, message = "Destino da doação deve ter no máximo 4000 caracteres")
    private String destinoDoacao;

    @NotNull(message = "Data da doação é obrigatória")
    private LocalDate dataDoacao;

    @Size(max = 50, message = "Status deve ter no máximo 50 caracteres")
    private String status;

    @NotNull(message = "ID do usuário é obrigatório")
    private Integer idUsuario;

    @NotNull(message = "ID da área de desastre é obrigatório")
    private Integer idAreaDesastre;

    private Integer idCampanhaHumanitaria;
}
