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
        LOGGER.info("Criando campanha humanitária");
        ValidatorUtils.validate(dto);

        Usuario usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getIdUsuario() + " não encontrado."));

        CampanhaHumanitaria campanha = toEntity(dto);
        campanha.setUsuario(usuario);

        campanhaRepository.salvar(campanha);
        return toDTO(campanha);
    }

    public CampanhaHumanitariaDTO buscarPorId(Integer id) {
        CampanhaHumanitaria campanha = campanhaRepository.buscarPorId(id);
        if (campanha == null) {
            throw new NotFoundException("Campanha Humanitária com ID " + id + " não encontrada.");
        }
        return toDTO(campanha);
    }

    @Transactional
    public CampanhaHumanitariaDTO atualizar(Integer id, CampanhaHumanitariaDTO dto) {
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

        return toDTO(campanhaRepository.salvar(campanha));
    }

    @Transactional
    public void deletar(Integer id) {
        if (!campanhaRepository.deletarPorId(id)) {
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
                .idUsuario(entity.getUsuario().getIdUsuario())
                .build();
    }

    private CampanhaHumanitaria toEntity(CampanhaHumanitariaDTO dto) {
        CampanhaHumanitaria entity = new CampanhaHumanitaria();
        entity.setDescricao(dto.getDescricao());
        entity.setStatusCampanha(dto.getStatusCampanha());
        entity.setPublicoAlvo(dto.getPublicoAlvo());
        entity.setTipoCampanha(dto.getTipoCampanha());
        entity.setResponsavel(dto.getResponsavel());
        entity.setDataInicio(dto.getDataInicio());
        entity.setDataFim(dto.getDataFim());
        return entity;
    }
}
