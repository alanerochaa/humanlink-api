package com.humanlink.service;

import com.humanlink.DTOs.RelatoDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.model.AreaDesastre;
import com.humanlink.model.Relato;
import com.humanlink.model.Usuario;
import com.humanlink.repository.AreaDesastreRepository;
import com.humanlink.repository.RelatoRepository;
import com.humanlink.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class RelatoService {

    @Inject
    RelatoRepository relatoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    AreaDesastreRepository areaDesastreRepository;

    public List<RelatoDTO> listarTodos() {
        List<Relato> relatos = relatoRepository.listarTodos();
        return relatos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<RelatoDTO> buscarPorId(Integer id) {
        Optional<Relato> relatoOpt = relatoRepository.buscarPorId(id);
        return relatoOpt.map(this::toDTO);
    }

    @Transactional
    public Integer criarRelato(RelatoDTO dto) {
        // Verificar existência do usuário
        Usuario usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getIdUsuario() + " não encontrado."));

        // Verificar existência da área de desastre
        AreaDesastre areaDesastre = areaDesastreRepository.buscarPorId(dto.getIdDesastre())
                .orElseThrow(() -> new NotFoundException("Área de desastre com ID " + dto.getIdDesastre() + " não encontrada."));

        Relato relato = toEntity(dto);
        relato.setUsuario(usuario);
        relato.setAreaDesastre(areaDesastre);

        LocalDateTime agora = LocalDateTime.now();
        relato.setDataCriacao(agora);
        relato.setDataAtualizacao(agora);

        relatoRepository.persistir(relato);
        return relato.getIdRelato();
    }

    @Transactional
    public RelatoDTO atualizar(Integer id, RelatoDTO dto) {
        Relato relatoExistente = relatoRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Relato com ID " + id + " não encontrado."));

        // Verificar existência do usuário (caso idUsuario seja diferente)
        if (!relatoExistente.getUsuario().getIdUsuario().equals(dto.getIdUsuario())) {
            Usuario usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                    .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getIdUsuario() + " não encontrado."));
            relatoExistente.setUsuario(usuario);
        }

        // Verificar existência da área de desastre (caso idDesastre seja diferente)
        if (!relatoExistente.getAreaDesastre().getIdDesastre().equals(dto.getIdDesastre())) {
            AreaDesastre areaDesastre = areaDesastreRepository.buscarPorId(dto.getIdDesastre())
                    .orElseThrow(() -> new NotFoundException("Área de desastre com ID " + dto.getIdDesastre() + " não encontrada."));
            relatoExistente.setAreaDesastre(areaDesastre);
        }

        // Atualizar campos restantes
        relatoExistente.setNome(dto.getNome());
        relatoExistente.setTitulo(dto.getTitulo());
        relatoExistente.setMensagem(dto.getMensagem());
        relatoExistente.setEndereco(dto.getEndereco());
        relatoExistente.setCidade(dto.getCidade());
        relatoExistente.setEstado(dto.getEstado());
        relatoExistente.setTipoDesastre(dto.getTipoDesastre());
        relatoExistente.setTipoDesastreOutro(dto.getTipoDesastreOutro());
        relatoExistente.setUrgencia(dto.getUrgencia());
        relatoExistente.setStatus(dto.getStatus());

        relatoExistente.setDataAtualizacao(LocalDateTime.now());

        Relato atualizado = relatoRepository.atualizar(relatoExistente);
        return toDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        boolean excluiu = relatoRepository.deletarPorId(id);
        if (!excluiu) {
            throw new NotFoundException("Relato com ID " + id + " não encontrado.");
        }
    }

    // Conversão de entidade para DTO
    private RelatoDTO toDTO(Relato relato) {
        if (relato == null) return null;

        return RelatoDTO.builder()
                .idRelato(relato.getIdRelato())
                .nome(relato.getNome())
                .titulo(relato.getTitulo())
                .mensagem(relato.getMensagem())
                .endereco(relato.getEndereco())
                .cidade(relato.getCidade())
                .estado(relato.getEstado())
                .tipoDesastre(relato.getTipoDesastre())
                .tipoDesastreOutro(relato.getTipoDesastreOutro())
                .urgencia(relato.getUrgencia())
                .status(relato.getStatus())
                .idUsuario(relato.getUsuario() != null ? relato.getUsuario().getIdUsuario() : null)
                .idDesastre(relato.getAreaDesastre() != null ? relato.getAreaDesastre().getIdDesastre() : null)
                .dataCriacao(relato.getDataCriacao())
                .dataAtualizacao(relato.getDataAtualizacao())
                .build();
    }

    // Conversão de DTO para entidade (sem relacionamentos)
    private Relato toEntity(RelatoDTO dto) {
        if (dto == null) return null;

        Relato relato = new Relato();
        relato.setNome(dto.getNome());
        relato.setTitulo(dto.getTitulo());
        relato.setMensagem(dto.getMensagem());
        relato.setEndereco(dto.getEndereco());
        relato.setCidade(dto.getCidade());
        relato.setEstado(dto.getEstado());
        relato.setTipoDesastre(dto.getTipoDesastre());
        relato.setTipoDesastreOutro(dto.getTipoDesastreOutro());
        relato.setUrgencia(dto.getUrgencia());
        relato.setStatus(dto.getStatus());
        // usuario e areaDesastre serão setados no método criarRelato
        return relato;
    }
}
