package com.humanlink.service;

import com.humanlink.DTOs.CampanhaHumanitariaDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.model.CampanhaHumanitaria;
import com.humanlink.model.Usuario;
import com.humanlink.repository.CampanhaHumanitariaRepository;
import com.humanlink.repository.UsuarioRepository;
import com.humanlink.util.ValidatorUtils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class CampanhaHumanitariaService {

    private static final Logger LOGGER = Logger.getLogger(CampanhaHumanitariaService.class.getName());

    @Inject
    private CampanhaHumanitariaRepository campanhaRepository;

    @Inject
    private UsuarioRepository usuarioRepository;

    public List<CampanhaHumanitariaDTO> listarTodos() {
        LOGGER.info("Recuperando todas campanhas humanitárias");
        return campanhaRepository.listarTodos().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CampanhaHumanitariaDTO criarCampanha(CampanhaHumanitariaDTO dto) {
        LOGGER.info("Criando campanha humanitária: " + dto.getDescricao());
        ValidatorUtils.validate(dto);

        CampanhaHumanitaria campanha = toEntity(dto);

        campanhaRepository.salvar(campanha);

        return toDTO(campanha);
    }

    public CampanhaHumanitariaDTO buscarPorId(Integer id) {
        LOGGER.info("Buscando campanha humanitária por ID: " + id);
        CampanhaHumanitaria campanha = campanhaRepository.buscarPorId(id);
        if (campanha == null) {
            throw new NotFoundException("Campanha Humanitária com ID " + id + " não encontrada.");
        }
        return toDTO(campanha);
    }

    @Transactional
    public CampanhaHumanitariaDTO atualizar(Integer id, CampanhaHumanitariaDTO dto) {
        LOGGER.info("Atualizando campanha humanitária com ID: " + id);
        CampanhaHumanitaria campanha = campanhaRepository.buscarPorId(id);
        if (campanha == null) {
            throw new NotFoundException("Campanha Humanitária com ID " + id + " não encontrada.");
        }

        if (dto.getDescricao() != null) campanha.setDescricao(dto.getDescricao());
        if (dto.getStatusCampanha() != null) campanha.setStatusCampanha(dto.getStatusCampanha());
        if (dto.getPublicoAlvo() != null) campanha.setPublicoAlvo(dto.getPublicoAlvo());
        if (dto.getTipoCampanha() != null) campanha.setTipoCampanha(dto.getTipoCampanha());
        if (dto.getResponsavel() != null) campanha.setResponsavel(dto.getResponsavel());
        if (dto.getDataInicio() != null) campanha.setDataInicio(dto.getDataInicio());
        if (dto.getDataFim() != null) campanha.setDataFim(dto.getDataFim());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                    .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getIdUsuario() + " não encontrado."));
            campanha.setUsuario(usuario);
        } else {
            campanha.setUsuario(null);
        }

        campanhaRepository.salvar(campanha);

        return toDTO(campanha);
    }

    @Transactional
    public void deletar(Integer id) {
        LOGGER.info("Deletando campanha humanitária com ID: " + id);
        boolean deleted = campanhaRepository.deletarPorId(id);
        if (!deleted) {
            throw new NotFoundException("Campanha Humanitária com ID " + id + " não encontrada.");
        }
    }

    private CampanhaHumanitariaDTO toDTO(CampanhaHumanitaria entity) {
        return CampanhaHumanitariaDTO.builder()
                .id(entity.getIdCampanha())
                .descricao(entity.getDescricao())
                .statusCampanha(entity.getStatusCampanha())
                .publicoAlvo(entity.getPublicoAlvo())
                .tipoCampanha(entity.getTipoCampanha())
                .responsavel(entity.getResponsavel())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .idUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null)
                .build();
    }

    private CampanhaHumanitaria toEntity(CampanhaHumanitariaDTO dto) {
        CampanhaHumanitaria campanha = new CampanhaHumanitaria();

        campanha.setDescricao(dto.getDescricao());
        campanha.setStatusCampanha(dto.getStatusCampanha());
        campanha.setPublicoAlvo(dto.getPublicoAlvo());
        campanha.setTipoCampanha(dto.getTipoCampanha());
        campanha.setResponsavel(dto.getResponsavel());
        campanha.setDataInicio(dto.getDataInicio());
        campanha.setDataFim(dto.getDataFim());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                    .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getIdUsuario() + " não encontrado."));
            campanha.setUsuario(usuario);
        }

        return campanha;
    }
}
