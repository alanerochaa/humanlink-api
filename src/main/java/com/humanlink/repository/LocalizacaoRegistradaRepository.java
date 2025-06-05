package com.humanlink.repository;

import com.humanlink.model.LocalizacaoRegistrada;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LocalizacaoRegistradaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<LocalizacaoRegistrada> listAll() {
        return em.createQuery("FROM LocalizacaoRegistrada", LocalizacaoRegistrada.class).getResultList();
    }

    public Optional<LocalizacaoRegistrada> findById(Integer id) {
        return Optional.ofNullable(em.find(LocalizacaoRegistrada.class, id));
    }

    @Transactional
    public LocalizacaoRegistrada persist(LocalizacaoRegistrada localizacao) {
        if (localizacao.getIdLocalizacao() == null) {
            em.persist(localizacao);
            return localizacao;
        } else {
            return em.merge(localizacao);
        }
    }

    @Transactional
    public boolean deleteById(Integer id) {
        LocalizacaoRegistrada localizacao = em.find(LocalizacaoRegistrada.class, id);
        if (localizacao != null) {
            em.remove(localizacao);
            return true;
        }
        return false;
    }
}
