package com.humanlink.service;

import com.humanlink.DTOs.LocalizacaoRegistradaDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.model.LocalizacaoRegistrada;
import com.humanlink.model.Usuario;
import com.humanlink.repository.LocalizacaoRegistradaRepository;
import com.humanlink.util.ValidatorUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LocalizacaoRegistradaService {

    @Inject
    LocalizacaoRegistradaRepository localizacaoRepository;

    public List<LocalizacaoRegistradaDTO> listarTodos() {
        return localizacaoRepository.listAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LocalizacaoRegistradaDTO criar(LocalizacaoRegistradaDTO dto) {
        ValidatorUtils.validate(dto);
        LocalizacaoRegistrada localizacao = toEntity(dto);
        localizacao.setDataRegistro(LocalDateTime.now());
        localizacaoRepository.persist(localizacao);
        return toDTO(localizacao);
    }

    public LocalizacaoRegistradaDTO buscarPorId(Integer id) {
        LocalizacaoRegistrada localizacao = localizacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Localização com ID " + id + " não encontrada."));
        return toDTO(localizacao);
    }

    @Transactional
    public LocalizacaoRegistradaDTO atualizar(Integer id, LocalizacaoRegistradaDTO dto) {
        LocalizacaoRegistrada localizacao = localizacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Localização com ID " + id + " não encontrada."));

        localizacao.setTipoLocal(dto.getTipoLocal());
        localizacao.setRiscoArea(dto.getRiscoArea());
        localizacao.setLatitude(dto.getLatitude());
        localizacao.setLongitude(dto.getLongitude());
        localizacao.setEndereco(dto.getEndereco());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(dto.getIdUsuario());
            localizacao.setUsuario(usuario);
        }

        localizacaoRepository.persist(localizacao);
        return toDTO(localizacao);
    }

    @Transactional
    public void deletar(Integer id) {
        boolean deletado = localizacaoRepository.deleteById(id);
        if (!deletado) {
            throw new NotFoundException("Localização com ID " + id + " não encontrada para exclusão.");
        }
    }

    private LocalizacaoRegistradaDTO toDTO(LocalizacaoRegistrada localizacao) {
        return LocalizacaoRegistradaDTO.builder()
                .id(localizacao.getIdLocalizacao())
                .tipoLocal(localizacao.getTipoLocal())
                .riscoArea(localizacao.getRiscoArea())
                .latitude(localizacao.getLatitude())
                .longitude(localizacao.getLongitude())
                .endereco(localizacao.getEndereco())
                .dataRegistro(localizacao.getDataRegistro())
                .idUsuario(localizacao.getUsuario() != null ? localizacao.getUsuario().getIdUsuario() : null)
                .build();
    }

    private LocalizacaoRegistrada toEntity(LocalizacaoRegistradaDTO dto) {
        LocalizacaoRegistrada localizacao = new LocalizacaoRegistrada();
        localizacao.setTipoLocal(dto.getTipoLocal());
        localizacao.setRiscoArea(dto.getRiscoArea());
        localizacao.setLatitude(dto.getLatitude());
        localizacao.setLongitude(dto.getLongitude());
        localizacao.setEndereco(dto.getEndereco());
        if (dto.getIdUsuario() != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(dto.getIdUsuario());
            localizacao.setUsuario(usuario);
        }
        return localizacao;
    }
}
