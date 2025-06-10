package com.jczap.dao;

import com.jczap.modelo.Empleado;
import java.util.List;

public interface EmpleadoDAO {
    List<Empleado> listarTodos();
    Empleado obtenerPorId(Integer id);
    void registrar(Empleado empleado);
    void actualizar(Empleado empleado);
    void eliminar(Integer id);
}