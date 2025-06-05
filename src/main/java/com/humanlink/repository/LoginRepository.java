package com.humanlink.repository;

import com.humanlink.model.Login;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LoginRepository {

    @Inject
    EntityManager em;

    public List<Login> listAll() {
        return em.createQuery("SELECT l FROM Login l", Login.class).getResultList();
    }

    public Optional<Login> buscarPorId(Integer id) {
        Login login = em.find(Login.class, id);
        return Optional.ofNullable(login);
    }

    @Transactional
    public void persist(Login login) {
        em.persist(login);
    }

    @Transactional
    public Login merge(Login login) {
        return em.merge(login);
    }

    @Transactional
    public boolean deleteById(Integer id) {
        Login login = em.find(Login.class, id);
        if (login != null) {
            em.remove(login);
            return true;
        }
        return false;
    }
}
