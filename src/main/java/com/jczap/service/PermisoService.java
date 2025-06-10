package com.jczap.service;

import com.jczap.modelo.Permiso;
import com.jczap.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermisoService {
    @Autowired
    private PermisoRepository permisoRepository;
    
    public List<Permiso> listarTodos() {
        return permisoRepository.findAll();
    }
    
    public List<Permiso> listarActivos() {
        return permisoRepository.findByActivoTrue();
    }
    
    public List<Permiso> obtenerPorCodigos(List<String> codigos) {
        return permisoRepository.findByCodigoIn(codigos);
    }
}