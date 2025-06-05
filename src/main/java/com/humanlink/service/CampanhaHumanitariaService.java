package com.humanlink.service;

import com.humanlink.DTOs.CampanhaHumanitariaDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.model.CampanhaHumanitaria;
import com.humanlink.model.Usuario;
import com.humanlink.repository.CampanhaHumanitariaRepository;
import com.humanlink.util.ValidatorUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CampanhaHumanitariaService {

    @Inject
    CampanhaHumanitariaRepository campanhaRepository;

    public List<CampanhaHumanitariaDTO> listarTodos() {
        return campanhaRepository.listAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CampanhaHumanitariaDTO criarCampanha(CampanhaHumanitariaDTO dto) {
        ValidatorUtils.validate(dto);
        CampanhaHumanitaria campanha = toEntity(dto);
        campanhaRepository.persist(campanha);
        return toDTO(campanha);
    }

    public CampanhaHumanitariaDTO buscarPorId(Integer id) {
        CampanhaHumanitaria campanha = campanhaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campanha Humanitária com ID " + id + " não encontrada."));
        return toDTO(campanha);
    }

    @Transactional
    public CampanhaHumanitariaDTO atualizar(Integer id, CampanhaHumanitariaDTO dto) {
        CampanhaHumanitaria campanha = campanhaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campanha Humanitária com ID " + id + " não encontrada."));

        campanha.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : campanha.getDescricao());
        campanha.setStatusCampanha(dto.getStatusCampanha() != null ? dto.getStatusCampanha() : campanha.getStatusCampanha());
        campanha.setPublicoAlvo(dto.getPublicoAlvo() != null ? dto.getPublicoAlvo() : campanha.getPublicoAlvo());
        campanha.setTipoCampanha(dto.getTipoCampanha() != null ? dto.getTipoCampanha() : campanha.getTipoCampanha());
        campanha.setResponsavel(dto.getResponsavel() != null ? dto.getResponsavel() : campanha.getResponsavel());
        campanha.setDataInicio(dto.getDataInicio() != null ? dto.getDataInicio() : campanha.getDataInicio());
        campanha.setDataFim(dto.getDataFim() != null ? dto.getDataFim() : campanha.getDataFim());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(dto.getIdUsuario());
            campanha.setUsuario(usuario);
        }

        campanhaRepository.persist(campanha);
        return toDTO(campanha);
    }

    @Transactional
    public void deletar(Integer id) {
        boolean deleted = campanhaRepository.deleteById(id);
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
        CampanhaHumanitaria entity = new CampanhaHumanitaria();
        entity.setDescricao(dto.getDescricao());
        entity.setStatusCampanha(dto.getStatusCampanha());
        entity.setPublicoAlvo(dto.getPublicoAlvo());
        entity.setTipoCampanha(dto.getTipoCampanha());
        entity.setResponsavel(dto.getResponsavel());
        entity.setDataInicio(dto.getDataInicio());
        entity.setDataFim(dto.getDataFim());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(dto.getIdUsuario());
            entity.setUsuario(usuario);
        }
        return entity;
    }
}
