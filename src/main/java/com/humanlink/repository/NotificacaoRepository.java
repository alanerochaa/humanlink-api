package com.humanlink.repository;

import com.humanlink.model.Notificacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NotificacaoRepository {

    @Inject
    EntityManager em;

    public Optional<Notificacao> buscarPorId(Integer id) {
        return Optional.ofNullable(em.find(Notificacao.class, id));
    }

    public List<Notificacao> listarTodos() {
        return em.createQuery("SELECT n FROM Notificacao n", Notificacao.class).getResultList();
    }

    @Transactional
    public void salvar(Notificacao notificacao) {
        em.persist(notificacao);
    }

    @Transactional
    public Notificacao atualizar(Notificacao notificacao) {
        return em.merge(notificacao);
    }

    @Transactional
    public boolean deletarPorId(Integer id) {
        Notificacao notificacao = em.find(Notificacao.class, id);
        if (notificacao != null) {
            em.remove(notificacao);
            return true;
        }
        return false;
    }
}
