package com.jczap.dao.impl;

import com.jczap.dao.UsuarioDAO;
import com.jczap.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UsuarioDAOImpl implements UsuarioDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> listarTodos() {
        return entityManager.createQuery("FROM Usuario", Usuario.class).getResultList();
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.persist(usuario);
    }

    @Override
    public void actualizar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public void eliminar(Integer id) {
        Usuario usuario = obtenerPorId(id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }
}
