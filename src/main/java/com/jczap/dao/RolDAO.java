package com.jczap.dao;

import com.jczap.modelo.Rol;
import java.util.List;

public interface RolDAO {
    List<Rol> listarTodos();
    Rol obtenerPorId(Integer id);
    void registrar(Rol rol);
    void actualizar(Rol rol);
    void eliminar(Integer id);
}
