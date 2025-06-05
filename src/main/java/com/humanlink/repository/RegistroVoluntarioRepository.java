package com.humanlink.repository;

import com.humanlink.model.RegistroVoluntario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RegistroVoluntarioRepository {

    @PersistenceContext
    private EntityManager em;

    public List<RegistroVoluntario> listAll() {
        TypedQuery<RegistroVoluntario> query = em.createQuery(
                "SELECT rv FROM RegistroVoluntario rv LEFT JOIN FETCH rv.usuario",
                RegistroVoluntario.class);
        return query.getResultList();
    }

    public RegistroVoluntario findById(Integer id) {
        TypedQuery<RegistroVoluntario> query = em.createQuery(
                "SELECT rv FROM RegistroVoluntario rv LEFT JOIN FETCH rv.usuario WHERE rv.id = :id",
                RegistroVoluntario.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Transactional
    public void persist(RegistroVoluntario registro) {
        em.persist(registro);
    }

    @Transactional
    public RegistroVoluntario update(RegistroVoluntario registro) {
        return em.merge(registro);
    }

    @Transactional
    public void delete(RegistroVoluntario registro) {
        em.remove(em.contains(registro) ? registro : em.merge(registro));
    }
}
