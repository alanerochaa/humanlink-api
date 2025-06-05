package com.humanlink.service;

import com.humanlink.DTOs.UsuarioDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.model.Usuario;
import com.humanlink.repository.UsuarioRepository;
import com.humanlink.util.ValidatorUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.listarTodos().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        ValidatorUtils.validate(usuarioDTO);

        Usuario usuario = toEntity(usuarioDTO);

        usuarioRepository.salvar(usuario);
        return toDTO(usuario);
    }

    public UsuarioDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));
        return toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO atualizar(Integer id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getCpf() != null) usuario.setCpf(dto.getCpf());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());
        if (dto.getTipoUsuario() != null) usuario.setTipoUsuario(dto.getTipoUsuario());


        Usuario atualizado = usuarioRepository.atualizar(usuario);
        return toDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        boolean existe = usuarioRepository.existePorId(id);
        if (!existe) {
            throw new NotFoundException("Usuário com ID " + id + " não encontrado para exclusão.");
        }
        usuarioRepository.deletarPorId(id);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setDataAtualizacao(usuario.getDataAtualizacao());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        return usuario;
    }
}
