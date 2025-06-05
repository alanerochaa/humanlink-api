package com.humanlink.repository;

import com.humanlink.model.Relato;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RelatoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Relato> listarTodos() {
        return em.createQuery("SELECT r FROM Relato r", Relato.class).getResultList();
    }

    public Optional<Relato> buscarPorId(Integer id) {
        Relato relato = em.find(Relato.class, id);
        return Optional.ofNullable(relato);
    }

    @Transactional
    public void persistir(Relato relato) {
        em.persist(relato);
    }

    @Transactional
    public Relato atualizar(Relato relato) {
        return em.merge(relato);
    }

    @Transactional
    public boolean deletarPorId(Integer id) {
        Relato relato = em.find(Relato.class, id);
        if (relato != null) {
            em.remove(relato);
            return true;
        }
        return false;
    }

    public boolean existePorId(Integer id) {
        Long count = em.createQuery("SELECT COUNT(r) FROM Relato r WHERE r.idRelato = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
