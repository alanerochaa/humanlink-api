package com.humanlink.repository;

import com.humanlink.model.AreaDesastre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AreaDesastreRepository {

    @PersistenceContext
    private EntityManager em;

    public List<AreaDesastre> listarTodos() {
        return em.createQuery("SELECT a FROM AreaDesastre a", AreaDesastre.class)
                .getResultList();
    }

    public Optional<AreaDesastre> buscarPorId(Integer id) {
        return Optional.ofNullable(em.find(AreaDesastre.class, id));
    }

    @Transactional
    public AreaDesastre salvarOuAtualizar(AreaDesastre area) {
        if (area.getIdDesastre() == null) {
            em.persist(area);
            return area;
        } else {
            return em.merge(area);
        }
    }

    @Transactional
    public boolean deletar(Integer id) {
        AreaDesastre existente = em.find(AreaDesastre.class, id);
        if (existente != null) {
            em.remove(existente);
            return true;
        }
        return false;
    }
}
