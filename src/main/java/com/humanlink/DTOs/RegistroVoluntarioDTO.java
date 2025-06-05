package com.humanlink.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroVoluntarioDTO {

    private Integer id;

    @NotBlank(message = "Nome do voluntário é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Área de atuação é obrigatória")
    @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
    private String tipoDeAjuda;

    @NotBlank(message = "Disponibilidade é obrigatória")
    @Size(max = 50, message = "Disponibilidade deve ter no máximo 50 caracteres")
    private String disponibilidade;

    @NotNull(message = "Data do registro é obrigatória")
    private LocalDateTime dataRegistro;

    @NotNull(message = "ID do usuário é obrigatório")
    private Integer idUsuario;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
}
