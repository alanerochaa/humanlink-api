package com.humanlink.repository;

import com.humanlink.model.RegistroDoacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RegistroDoacaoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<RegistroDoacao> listAll() {
        TypedQuery<RegistroDoacao> query = em.createQuery(
                "SELECT rd FROM RegistroDoacao rd LEFT JOIN FETCH rd.usuario LEFT JOIN FETCH rd.areaDeDesastre LEFT JOIN FETCH rd.campanhaHumanitaria",
                RegistroDoacao.class);
        return query.getResultList();
    }

    public RegistroDoacao findById(Integer id) {
        TypedQuery<RegistroDoacao> query = em.createQuery(
                "SELECT rd FROM RegistroDoacao rd LEFT JOIN FETCH rd.usuario LEFT JOIN FETCH rd.areaDeDesastre LEFT JOIN FETCH rd.campanhaHumanitaria WHERE rd.id = :id",
                RegistroDoacao.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Transactional
    public void persist(RegistroDoacao registro) {
        em.persist(registro);
    }

    @Transactional
    public RegistroDoacao update(RegistroDoacao registro) {
        return em.merge(registro);
    }

    @Transactional
    public void delete(RegistroDoacao registro) {
        em.remove(em.contains(registro) ? registro : em.merge(registro));
    }
}
