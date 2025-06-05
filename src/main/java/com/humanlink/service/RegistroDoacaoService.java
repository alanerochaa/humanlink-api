package com.humanlink.service;

import com.humanlink.DTOs.RegistroDoacaoDTO;
import com.humanlink.model.CampanhaHumanitaria;
import com.humanlink.model.RegistroDoacao;
import com.humanlink.model.Usuario;
import com.humanlink.model.AreaDesastre;
import com.humanlink.util.ValidatorUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RegistroDoacaoService {

    @PersistenceContext
    private EntityManager em;

    public List<RegistroDoacaoDTO> listAll() {
        List<RegistroDoacao> doacoes = em.createQuery(
                "SELECT r FROM RegistroDoacao r LEFT JOIN FETCH r.usuario LEFT JOIN FETCH r.areaDesastre LEFT JOIN FETCH r.campanhaHumanitaria",
                RegistroDoacao.class).getResultList();

        return doacoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RegistroDoacaoDTO findById(Integer id) {
        RegistroDoacao doacao = em.createQuery(
                        "SELECT r FROM RegistroDoacao r LEFT JOIN FETCH r.usuario LEFT JOIN FETCH r.areaDesastre LEFT JOIN FETCH r.campanhaHumanitaria WHERE r.idDoacao = :id",
                        RegistroDoacao.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("RegistroDoacao não encontrado"));

        return toDTO(doacao);
    }

    @Transactional
    public RegistroDoacaoDTO create(RegistroDoacaoDTO dto) {
        ValidatorUtils.validate(dto);

        RegistroDoacao doacao = new RegistroDoacao();
        doacao.setTipoDoacao(dto.getTipoDoacao());
        doacao.setQuantidadeDoacao(dto.getQuantidadeDoacao());
        doacao.setDescricao(dto.getDescricao());
        doacao.setDestinoDoacao(dto.getDestinoDoacao());

        // conversão LocalDate para LocalDateTime
        LocalDate dataDoacaoLocalDate = dto.getDataDoacao();
        if (dataDoacaoLocalDate != null) {
            doacao.setDataDoacao(dataDoacaoLocalDate.atStartOfDay());
        } else {
            doacao.setDataDoacao(null);
        }

        doacao.setStatus(dto.getStatus());

        Usuario usuario = em.find(Usuario.class, dto.getIdUsuario());
        if (usuario == null) throw new BadRequestException("Usuário inválido");
        doacao.setUsuario(usuario);

        AreaDesastre area = em.find(AreaDesastre.class, dto.getIdAreaDesastre());
        if (area == null) throw new BadRequestException("Área de desastre inválida");
        doacao.setAreaDesastre(area);

        if (dto.getIdCampanhaHumanitaria() != null) {
            CampanhaHumanitaria campanha = em.find(CampanhaHumanitaria.class, dto.getIdCampanhaHumanitaria());
            if (campanha == null) throw new BadRequestException("Campanha inválida");
            doacao.setCampanhaHumanitaria(campanha);
        } else {
            doacao.setCampanhaHumanitaria(null);
        }

        em.persist(doacao);
        return toDTO(doacao);
    }

    @Transactional
    public RegistroDoacaoDTO update(Integer id, RegistroDoacaoDTO dto) {
        ValidatorUtils.validate(dto);

        RegistroDoacao doacao = em.find(RegistroDoacao.class, id);
        if (doacao == null) throw new NotFoundException("RegistroDoacao não encontrado.");

        doacao.setTipoDoacao(dto.getTipoDoacao());
        doacao.setQuantidadeDoacao(dto.getQuantidadeDoacao());
        doacao.setDescricao(dto.getDescricao());
        doacao.setDestinoDoacao(dto.getDestinoDoacao());

        // conversão LocalDate para LocalDateTime no update
        LocalDate dataDoacaoLocalDate = dto.getDataDoacao();
        if (dataDoacaoLocalDate != null) {
            doacao.setDataDoacao(dataDoacaoLocalDate.atStartOfDay());
        } else {
            doacao.setDataDoacao(null);
        }

        doacao.setStatus(dto.getStatus());

        Usuario usuario = em.find(Usuario.class, dto.getIdUsuario());
        if (usuario == null) throw new BadRequestException("Usuário inválido");
        doacao.setUsuario(usuario);

        AreaDesastre area = em.find(AreaDesastre.class, dto.getIdAreaDesastre());
        if (area == null) throw new BadRequestException("Área de desastre inválida");
        doacao.setAreaDesastre(area);

        if (dto.getIdCampanhaHumanitaria() != null) {
            CampanhaHumanitaria campanha = em.find(CampanhaHumanitaria.class, dto.getIdCampanhaHumanitaria());
            if (campanha == null) throw new BadRequestException("Campanha inválida");
            doacao.setCampanhaHumanitaria(campanha);
        } else {
            doacao.setCampanhaHumanitaria(null);
        }

        return toDTO(doacao);
    }

    @Transactional
    public void delete(Integer id) {
        RegistroDoacao doacao = em.find(RegistroDoacao.class, id);
        if (doacao == null) throw new NotFoundException("RegistroDoacao não encontrado.");
        em.remove(doacao);
    }

    private RegistroDoacaoDTO toDTO(RegistroDoacao doacao) {
        return RegistroDoacaoDTO.builder()
                .id(doacao.getIdDoacao())
                .tipoDoacao(doacao.getTipoDoacao())
                .quantidadeDoacao(doacao.getQuantidadeDoacao())
                .descricao(doacao.getDescricao())
                .destinoDoacao(doacao.getDestinoDoacao())
                .dataDoacao(doacao.getDataDoacao() != null ? doacao.getDataDoacao().toLocalDate() : null)
                .status(doacao.getStatus())
                .idUsuario(doacao.getUsuario() != null ? doacao.getUsuario().getIdUsuario() : null)
                .idAreaDesastre(doacao.getAreaDesastre() != null ? doacao.getAreaDesastre().getIdDesastre() : null)
                .idCampanhaHumanitaria(doacao.getCampanhaHumanitaria() != null ? doacao.getCampanhaHumanitaria().getIdCampanha() : null)
                .build();
    }
}
