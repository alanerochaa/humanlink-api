package com.humanlink.repository;

import com.humanlink.model.CampanhaHumanitaria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CampanhaHumanitariaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CampanhaHumanitaria> listarTodos() {
        return em.createQuery("FROM CampanhaHumanitaria", CampanhaHumanitaria.class)
                .getResultList();
    }

    public CampanhaHumanitaria buscarPorId(Integer id) {
        return em.find(CampanhaHumanitaria.class, id);
    }

    @Transactional
    public CampanhaHumanitaria salvar(CampanhaHumanitaria campanha) {
        if (campanha.getIdCampanha() == null) {
            em.persist(campanha);
            return campanha;
        } else {
            return em.merge(campanha);
        }
    }

    @Transactional
    public boolean deletarPorId(Integer id) {
        CampanhaHumanitaria campanha = em.find(CampanhaHumanitaria.class, id);
        if (campanha != null) {
            em.remove(campanha);
            return true;
        }
        return false;
    }
}
