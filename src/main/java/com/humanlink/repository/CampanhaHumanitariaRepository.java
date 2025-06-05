package com.humanlink.repository;

import com.humanlink.model.CampanhaHumanitaria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CampanhaHumanitariaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CampanhaHumanitaria> listAll() {
        return em.createQuery("FROM CampanhaHumanitaria", CampanhaHumanitaria.class).getResultList();
    }

    public Optional<CampanhaHumanitaria> findById(Integer id) {
        return Optional.ofNullable(em.find(CampanhaHumanitaria.class, id));
    }

    @Transactional
    public CampanhaHumanitaria persist(CampanhaHumanitaria campanha) {
        if (campanha.getIdCampanha() == null) {
            em.persist(campanha);
            return campanha;
        } else {
            return em.merge(campanha);
        }
    }

    @Transactional
    public boolean deleteById(Integer id) {
        CampanhaHumanitaria campanha = em.find(CampanhaHumanitaria.class, id);
        if (campanha != null) {
            em.remove(campanha);
            return true;
        }
        return false;
    }
}
