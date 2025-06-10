package com.jczap.dao.impl;

import com.jczap.dao.RolDAO;
import com.jczap.modelo.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class RolDAOImpl implements RolDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Rol> listarTodos() {
        return entityManager.createQuery("FROM Rol", Rol.class).getResultList();
    }

    @Override
    public Rol obtenerPorId(Integer id) {
        return entityManager.find(Rol.class, id);
    }

    @Override
    public void registrar(Rol rol) {
        entityManager.persist(rol);
    }

    @Override
    public void actualizar(Rol rol) {
        entityManager.merge(rol);
    }

    @Override
    public void eliminar(Integer id) {
        Rol rol = obtenerPorId(id);
        if (rol != null) {
            entityManager.remove(rol);
        }
    }
}
