package com.jczap.dao.impl;

import com.jczap.dao.EmpleadoDAO;
import com.jczap.modelo.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EmpleadoDAOImpl implements EmpleadoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Empleado> listarTodos() {
        return entityManager.createQuery("FROM Empleado", Empleado.class).getResultList();
    }

    @Override
    public Empleado obtenerPorId(Integer id) {
        return entityManager.find(Empleado.class, id);
    }

    @Override
    public void registrar(Empleado empleado) {
        entityManager.persist(empleado);
    }

    @Override
    public void actualizar(Empleado empleado) {
        entityManager.merge(empleado);
    }

    @Override
    public void eliminar(Integer id) {
        Empleado empleado = obtenerPorId(id);
        if (empleado != null) {
            entityManager.remove(empleado);
        }
    }
}
