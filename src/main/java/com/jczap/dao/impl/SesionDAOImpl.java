package com.jczap.dao.impl;

import com.jczap.dao.SesionDAO;
import com.jczap.modelo.Sesion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class SesionDAOImpl implements SesionDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Sesion> listarTodos() {
        return entityManager.createQuery("FROM Sesion", Sesion.class).getResultList();
    }
    
    @Override
    public Sesion obtenerPorId(Integer id) {
        return entityManager.find(Sesion.class, id);
    }
    
    @Override
    public void registrar(Sesion sesion) {
        entityManager.persist(sesion);
    }
    
    @Override
    public void actualizar(Sesion sesion) {
        entityManager.merge(sesion);
    }
    
    @Override
    public void eliminar(Integer id) {
        Sesion sesion = obtenerPorId(id);
        if (sesion != null) {
            entityManager.remove(sesion);
        }
    }
}