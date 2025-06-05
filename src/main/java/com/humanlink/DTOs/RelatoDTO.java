package com.humanlink.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatoDTO {

    @Schema(example = "1", description = "Identificador único do relato")
    @JsonProperty("id_relato")
    private Integer idRelato;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(max = 255)
    @Schema(example = "João Silva", description = "Nome de quem fez o relato")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O campo titulo é obrigatório")
    @Size(max = 255)
    @Schema(example = "Desabamento em área residencial", description = "Título do relato")
    @JsonProperty("titulo")
    private String titulo;

    @NotBlank(message = "O campo mensagem é obrigatório")
    @Size(max = 4000)
    @Schema(example = "Casas desabaram após o terremoto...", description = "Descrição detalhada do relato")
    @JsonProperty("mensagem")
    private String mensagem;

    @Size(max = 500)
    @Schema(example = "Rua Principal, 123", description = "Endereço do local do desastre")
    @JsonProperty("endereco")
    private String endereco;

    @NotBlank(message = "O campo cidade é obrigatório")
    @Size(max = 100)
    @Schema(example = "São Paulo", description = "Cidade do desastre")
    @JsonProperty("cidade")
    private String cidade;

    @NotBlank(message = "O campo estado é obrigatório")
    @Size(max = 50)
    @Schema(example = "SP", description = "Estado do desastre")
    @JsonProperty("estado")
    private String estado;

    @NotBlank(message = "O campo tipo_desastre é obrigatório")
    @Size(max = 100)
    @Schema(example = "Terremoto", description = "Tipo do desastre")
    @JsonProperty("tipo_desastre")
    private String tipoDesastre;

    @Size(max = 255)
    @Schema(example = "Deslizamento", description = "Tipo de desastre quando outro")
    @JsonProperty("tipo_desastre_outro")
    private String tipoDesastreOutro;

    @Size(max = 50)
    @Schema(example = "ALTA", description = "Urgência do relato")
    @JsonProperty("urgencia")
    private String urgencia;

    @Size(max = 50)
    @Schema(example = "ABERTO", description = "Status do relato")
    @JsonProperty("status")
    private String status;

    @NotNull(message = "O campo id_usuario é obrigatório")
    @Schema(example = "1", description = "ID do usuário que criou o relato")
    @JsonProperty("id_usuario")
    private Integer idUsuario;

    @NotNull(message = "O campo id_desastre é obrigatório")
    @Schema(example = "1", description = "ID da área de desastre relacionada")
    @JsonProperty("id_desastre")
    private Integer idDesastre;

    @Schema(example = "2024-05-28T14:30:00", description = "Data de criação do relato")
    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    @Schema(example = "2024-05-28T15:30:00", description = "Data da última atualização do relato")
    @JsonProperty("data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
