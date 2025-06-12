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
        
        if (rolRequest.getPermisoIds() != null && !rolRequest.getPermisoIds().isEmpty()) {
            Set<Permiso> permisos = new HashSet<>(permisoRepository.findAllById(rolRequest.getPermisoIds()));
            rol.setPermisos(permisos);
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
        
        if (rolRequest.getPermisoIds() != null) {
            Set<Permiso> permisos = new HashSet<>(permisoRepository.findAllById(rolRequest.getPermisoIds()));
            rol.setPermisos(permisos);
        }
        
        return rolRepository.save(rol);
    }
    
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
    
    public RolResponse toResponse(Rol rol) {
        if (rol == null) {
            return null;
        }
        
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        response.setDescripcion(rol.getDescripcion());
        response.setActivo(rol.getActivo());
        
        if (rol.getPermisos() != null) {
            Set<RolResponse.PermisoResponse> permisosResponse = rol.getPermisos().stream()
                .map(permiso -> {
                    RolResponse.PermisoResponse permisoResponse = new RolResponse.PermisoResponse();
                    permisoResponse.setId(permiso.getId());
                    permisoResponse.setNombre(permiso.getNombre());
                    permisoResponse.setCodigo(permiso.getCodigo());
                    permisoResponse.setDescripcion(permiso.getDescripcion());
                    permisoResponse.setCategoria(permiso.getCategoria());
                    return permisoResponse;
                })
                .collect(Collectors.toSet());
            response.setPermisos(permisosResponse);
        }
        
        return response;
    }
}