package com.jczap.service;

import com.jczap.modelo.Rol;
import com.jczap.modelo.Permiso;
import com.jczap.dto.RolRequest;
import com.jczap.dto.RolResponse;
import com.jczap.repository.RolRepository;
import com.jczap.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PermisoRepository permisoRepository;
    
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }
    
    public Rol obtenerPorId(Integer id) {
        return rolRepository.findByIdWithPermisos(id).orElse(null);
    }
    
    @Transactional
    public Rol registrar(RolRequest rolRequest) {
        Rol rol = new Rol();
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        rol.setActivo(rolRequest.getActivo());
        
        if (rolRequest.getPermisos() != null && !rolRequest.getPermisos().isEmpty()) {
            List<Permiso> permisos = permisoRepository.findByCodigoIn(rolRequest.getPermisos());
            rol.setPermisos(new HashSet<>(permisos));
        }
        
        return rolRepository.save(rol);
    }
    
    @Transactional
    public Rol actualizar(Integer id, RolRequest rolRequest) {
        Rol rol = rolRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Rol no encontrado"));
        
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        rol.setActivo(rolRequest.getActivo());
        
        // Actualizar permisos
        rol.getPermisos().clear();
        if (rolRequest.getPermisos() != null && !rolRequest.getPermisos().isEmpty()) {
            List<Permiso> permisos = permisoRepository.findByCodigoIn(rolRequest.getPermisos());
            rol.setPermisos(new HashSet<>(permisos));
        }
        
        return rolRepository.save(rol);
    }
    
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
    
    // Método para convertir Rol a RolResponse
    public RolResponse toResponse(Rol rol) {
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        response.setDescripcion(rol.getDescripcion());
        response.setActivo(rol.getActivo());
        
        List<String> codigosPermisos = rol.getPermisos().stream()
            .map(Permiso::getCodigo)
            .collect(Collectors.toList());
        response.setPermisos(codigosPermisos);
        
        return response;
    }
}