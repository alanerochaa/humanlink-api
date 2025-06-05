package com.humanlink.service;

import com.humanlink.DTOs.LoginDTO;
import com.humanlink.exception.NotFoundException;
import com.humanlink.model.Login;
import com.humanlink.model.Usuario;
import com.humanlink.repository.LoginRepository;
import com.humanlink.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LoginService {

    @Inject
    LoginRepository loginRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public List<LoginDTO> listarTodos() {
        return loginRepository.listAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LoginDTO criarLogin(LoginDTO dto) {
        com.humanlink.util.ValidatorUtils.validate(dto);

        Usuario usuario = usuarioRepository.buscarPorId(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getUsuarioId() + " não encontrado."));

        Login login = toEntity(dto);
        login.setUsuario(usuario);

        if (login.getDataCriacao() == null) login.setDataCriacao(LocalDateTime.now());
        if (login.getDataAtualizacao() == null) login.setDataAtualizacao(LocalDateTime.now());

        loginRepository.persist(login);

        return toDTO(login);
    }

    public LoginDTO buscarPorId(Integer id) {
        Login login = loginRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Login com ID " + id + " não encontrado."));
        return toDTO(login);
    }

    @Transactional
    public LoginDTO atualizar(Integer id, LoginDTO dto) {
        Login login = loginRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Login com ID " + id + " não encontrado."));

        if (dto.getUsuarioId() != null && !dto.getUsuarioId().equals(login.getUsuario().getIdUsuario())) {
            Usuario usuario = usuarioRepository.buscarPorId(dto.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuário com ID " + dto.getUsuarioId() + " não encontrado."));
            login.setUsuario(usuario);
        }

        if (dto.getEmail() != null) login.setEmail(dto.getEmail());
        if (dto.getSenha() != null) login.setSenha(dto.getSenha());
        if (dto.getStatus() != null) login.setStatus(dto.getStatus());

        login.setDataAtualizacao(LocalDateTime.now());

        loginRepository.merge(login);

        return toDTO(login);
    }

    @Transactional
    public void deletar(Integer id) {
        boolean deleted = loginRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Login com ID " + id + " não encontrado para exclusão.");
        }
    }

    private LoginDTO toDTO(Login login) {
        LoginDTO dto = new LoginDTO();
        dto.setIdLogin(login.getIdLogin());
        dto.setEmail(login.getEmail());
        dto.setSenha(null); // para não retornar a senha
        dto.setStatus(login.getStatus());
        dto.setUsuarioId(login.getUsuario() != null ? login.getUsuario().getIdUsuario() : null);
        dto.setDataCriacao(login.getDataCriacao());
        dto.setDataAtualizacao(login.getDataAtualizacao());
        return dto;
    }

    private Login toEntity(LoginDTO dto) {
        Login login = new Login();
        if (dto.getIdLogin() != null) login.setIdLogin(dto.getIdLogin());
        login.setEmail(dto.getEmail());
        login.setSenha(dto.getSenha());
        login.setStatus(dto.getStatus());
        login.setDataCriacao(dto.getDataCriacao());
        login.setDataAtualizacao(dto.getDataAtualizacao());
        return login;
    }
}
