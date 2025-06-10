package com.jczap.service;

import com.jczap.modelo.Sesion;
import com.jczap.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SesionService {
	@Autowired
	
	private SesionRepository sesionRepository;
	
	public List<Sesion> listarTodos() {
        return sesionRepository.findAll();
    }
	
	public Sesion obtenerPorId(Integer id) {
        return sesionRepository.findById(id).orElse(null);
    }
    
    public void registrar(Sesion empleado) {
    	sesionRepository.save(empleado);
    }
    
    public void actualizar(Sesion empleado) {
    	sesionRepository.save(empleado);
    }
    
    public void eliminar(Integer id) {
    	sesionRepository.deleteById(id);
    }

}
