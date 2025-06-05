package com.humanlink.service;

import com.humanlink.DTOs.RegistroVoluntarioDTO;
import com.humanlink.model.RegistroVoluntario;
import com.humanlink.model.Usuario;
import com.humanlink.util.ValidatorUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RegistroVoluntarioService {

    @PersistenceContext
    private EntityManager em;

    public List<RegistroVoluntarioDTO> listAll() {
        List<RegistroVoluntario> voluntarios = em.createQuery(
                "SELECT rv FROM RegistroVoluntario rv LEFT JOIN FETCH rv.usuario",
                RegistroVoluntario.class).getResultList();

        return voluntarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RegistroVoluntarioDTO findById(Integer id) {
        RegistroVoluntario voluntario = em.createQuery(
                        "SELECT rv FROM RegistroVoluntario rv LEFT JOIN FETCH rv.usuario WHERE rv.id = :id",
                        RegistroVoluntario.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("RegistroVoluntario não encontrado"));

        return toDTO(voluntario);
    }

    @Transactional
    public RegistroVoluntarioDTO create(RegistroVoluntarioDTO dto) {
        ValidatorUtils.validate(dto);

        RegistroVoluntario voluntario = new RegistroVoluntario();
        voluntario.setNome(dto.getNome());
        voluntario.setTipo_ajuda(dto.getTipoDeAjuda());
        voluntario.setDisponibilidade(dto.getDisponibilidade());
        voluntario.setDataRegistro(dto.getDataRegistro());
        voluntario.setTelefone(dto.getTelefone());
        voluntario.setEmail(dto.getEmail());

        Usuario usuario = em.find(Usuario.class, dto.getIdUsuario());
        if (usuario == null) throw new BadRequestException("Usuário inválido");
        voluntario.setUsuario(usuario);

        em.persist(voluntario);
        return toDTO(voluntario);
    }

    @Transactional
    public RegistroVoluntarioDTO update(Integer id, RegistroVoluntarioDTO dto) {
        ValidatorUtils.validate(dto);

        RegistroVoluntario voluntario = em.find(RegistroVoluntario.class, id);
        if (voluntario == null) throw new NotFoundException("RegistroVoluntario não encontrado.");

        voluntario.setNome(dto.getNome());
        voluntario.setTipo_ajuda(dto.getTipoDeAjuda());
        voluntario.setDisponibilidade(dto.getDisponibilidade());
        voluntario.setDataRegistro(dto.getDataRegistro());
        voluntario.setTelefone(dto.getTelefone());
        voluntario.setEmail(dto.getEmail());

        Usuario usuario = em.find(Usuario.class, dto.getIdUsuario());
        if (usuario == null) throw new BadRequestException("Usuário inválido");
        voluntario.setUsuario(usuario);

        return toDTO(voluntario);
    }

    @Transactional
    public void delete(Integer id) {
        RegistroVoluntario voluntario = em.find(RegistroVoluntario.class, id);
        if (voluntario == null) throw new NotFoundException("RegistroVoluntario não encontrado.");
        em.remove(voluntario);
    }

    private RegistroVoluntarioDTO toDTO(RegistroVoluntario voluntario) {
        return RegistroVoluntarioDTO.builder()
                .id(voluntario.getId())
                .nome(voluntario.getNome())
                .tipoDeAjuda(voluntario.getTipo_ajuda())
                .disponibilidade(voluntario.getDisponibilidade())
                .dataRegistro(voluntario.getDataRegistro())
                .telefone(voluntario.getTelefone())
                .email(voluntario.getEmail())
                .idUsuario(voluntario.getUsuario() != null ? voluntario.getUsuario().getIdUsuario() : null)
                .build();
    }
}
