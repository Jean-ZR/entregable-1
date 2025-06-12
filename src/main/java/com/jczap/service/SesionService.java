package com.jczap.service;

import com.jczap.modelo.Sesion;
import com.jczap.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SesionService {
	private static final Logger logger = LoggerFactory.getLogger(SesionService.class);
	
	@Autowired
	private SesionRepository sesionRepository;
	
	public List<Sesion> listarTodas() {
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

    public Optional<Sesion> findByToken(String token) {
        return sesionRepository.findByToken(token);
    }
    
    public List<Sesion> findByUsuario(Integer usuarioId) {
        return sesionRepository.findByUsuarioId(usuarioId);
    }
    
    @Transactional
    public Sesion cerrarSesion(Integer id) {
        logger.info("Cerrando sesión con ID: {}", id);
        
        Sesion sesion = sesionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        
        LocalDateTime ahora = LocalDateTime.now();
        sesion.setActiva(false);
        sesion.setFechaFin(ahora);
        // Establecer la fecha de expiración a 24 horas desde ahora
        sesion.setFechaExpiracion(ahora.plusHours(24));
        
        logger.info("Sesión cerrada exitosamente: {}", sesion.getId());
        return sesionRepository.save(sesion);
    }
    
    @Transactional
    public void cerrarTodasLasSesiones(Integer usuarioId) {
        logger.info("Cerrando todas las sesiones del usuario: {}", usuarioId);
        
        List<Sesion> sesiones = sesionRepository.findByUsuarioId(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        
        for (Sesion sesion : sesiones) {
            if (sesion.getActiva()) {
                sesion.setActiva(false);
                sesion.setFechaFin(ahora);
                // Establecer la fecha de expiración a 24 horas desde ahora
                sesion.setFechaExpiracion(ahora.plusHours(24));
            }
        }
        
        sesionRepository.saveAll(sesiones);
        logger.info("Todas las sesiones del usuario {} han sido cerradas", usuarioId);
    }
    
    public boolean verificarSesion(String token) {
        Optional<Sesion> sesionOpt = sesionRepository.findByToken(token);
        if (sesionOpt.isPresent()) {
            Sesion sesion = sesionOpt.get();
            return sesion.getActiva() && !sesion.isExpired();
        }
        return false;
    }
}
