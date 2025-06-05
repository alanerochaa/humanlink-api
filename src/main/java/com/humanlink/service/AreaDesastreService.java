package com.humanlink.service;

import com.humanlink.model.AreaDesastre;
import com.humanlink.repository.AreaDesastreRepository;
import com.humanlink.util.ValidatorUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AreaDesastreService {

    @Inject
    AreaDesastreRepository repository;

    public List<AreaDesastre> listarTodos() {
        return repository.listarTodos();
    }

    public Optional<AreaDesastre> buscarPorId(Integer id) {
        return repository.buscarPorId(id);
    }

    @Transactional
    public AreaDesastre criar(AreaDesastre area) {
        ValidatorUtils.validate(area);
        return repository.salvar(area);
    }

    @Transactional
    public AreaDesastre atualizar(Integer id, AreaDesastre novaArea) {
        ValidatorUtils.validate(novaArea);
        AreaDesastre existente = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Área de desastre não encontrada para o ID: " + id));

        existente.setTipoDesastre(novaArea.getTipoDesastre());
        existente.setDescricao(novaArea.getDescricao());
        existente.setPrioridadeResposta(novaArea.getPrioridadeResposta());
        existente.setStatusEmergencia(novaArea.getStatusEmergencia());
        existente.setLatitude(novaArea.getLatitude());
        existente.setLongitude(novaArea.getLongitude());
        existente.setDataOcorrencia(novaArea.getDataOcorrencia());

        return repository.atualizar(existente);
    }

    @Transactional
    public boolean deletar(Integer id) {
        return repository.deletar(id);
    }
}
