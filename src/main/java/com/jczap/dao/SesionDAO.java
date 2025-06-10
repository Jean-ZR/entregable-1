package com.jczap.dao;

import com.jczap.modelo.Sesion;
import java.util.List;

public interface SesionDAO {
    List<Sesion> listarTodos();
    Sesion obtenerPorId(Integer id);
    void registrar(Sesion sesion);
    void actualizar(Sesion sesion);
    void eliminar(Integer id);
}