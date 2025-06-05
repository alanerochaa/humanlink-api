package com.humanlink.service;

import com.humanlink.DTOs.NotificacaoDTO;
import com.humanlink.model.Notificacao;
import com.humanlink.model.Usuario;
import com.humanlink.repository.NotificacaoRepository;
import com.humanlink.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NotificacaoService {

    @Inject
    NotificacaoRepository notificacaoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public Notificacao buscarNotificacaoPeloId(Integer id) {
        return notificacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada com id " + id));
    }

    public List<Notificacao> listarNotificacoes() {
        return notificacaoRepository.listarTodos();
    }

    @Transactional
    public Notificacao cadastrarNotificacao(NotificacaoDTO dto) {
        Usuario usuario = null;
        if (dto.getIdUsuario() != null) {
            usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado com id " + dto.getIdUsuario()));
        }

        Notificacao notificacao = Notificacao.builder()
                .mensagem(dto.getMensagem())
                .canalEnvio(dto.getCanalEnvio())
                .dataEnvio(dto.getDataEnvio() != null ? dto.getDataEnvio() : LocalDateTime.now())
                .status(dto.getStatus() != null ? dto.getStatus() : "PENDENTE")
                .usuario(usuario)
                .build();

        notificacaoRepository.salvar(notificacao);
        return notificacao;
    }

    @Transactional
    public Notificacao alterarNotificacao(Integer id, NotificacaoDTO dto) {
        Notificacao notificacao = notificacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada com id " + id));

        notificacao.setMensagem(dto.getMensagem());
        notificacao.setCanalEnvio(dto.getCanalEnvio());
        notificacao.setDataEnvio(dto.getDataEnvio() != null ? dto.getDataEnvio() : LocalDateTime.now());
        notificacao.setStatus(dto.getStatus());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.buscarPorId(dto.getIdUsuario())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado com id " + dto.getIdUsuario()));
            notificacao.setUsuario(usuario);
        } else {
            notificacao.setUsuario(null);
        }

        notificacaoRepository.atualizar(notificacao);
        return notificacao;
    }

    @Transactional
    public void deletarNotificacao(Integer id) {
        boolean deleted = notificacaoRepository.deletarPorId(id);
        if (!deleted) {
            throw new NotFoundException("Notificação não encontrada com id " + id);
        }
    }
}
