package com.jczap.dao;

import com.jczap.modelo.Usuario;
import java.util.List;

public interface UsuarioDAO {
    List<Usuario> listarTodos();
    Usuario obtenerPorId(Integer id);
    void registrar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(Integer id);
}
