package com.jczap.service;
import com.jczap.modelo.Rol;
import com.jczap.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }
    
    public Rol obtenerPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }
    
    public void registrar(Rol rol) {
        rolRepository.save(rol);
    }
    
    public void actualizar(Rol rol) {
        rolRepository.save(rol);
    }
    
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }
}