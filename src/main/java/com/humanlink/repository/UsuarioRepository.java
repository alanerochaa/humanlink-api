package com.humanlink.repository;

import com.humanlink.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository {

    @Inject
    EntityManager em;

    public List<Usuario> listarTodos() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        Usuario usuario = em.find(Usuario.class, id);
        return Optional.ofNullable(usuario);
    }

    @Transactional
    public void salvar(Usuario usuario) {
        em.persist(usuario);
    }

    @Transactional
    public Usuario atualizar(Usuario usuario) {
        return em.merge(usuario);
    }

    public boolean existePorId(Integer id) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.idUsuario = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public void deletarPorId(Integer id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
    }
}
