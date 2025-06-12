package com.jczap.controller;

import com.jczap.modelo.Sesion;
import com.jczap.service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin(origins = "*")
public class SesionController {
    private static final Logger logger = LoggerFactory.getLogger(SesionController.class);
    
    @Autowired
    private SesionService sesionService;
    
    @GetMapping
    public List<Sesion> listarTodas() {
        return sesionService.listarTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable("id") Integer id) {
        try {
            Sesion sesion = sesionService.obtenerPorId(id);
            if (sesion == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(sesion);
        } catch (Exception e) {
            logger.error("Error al obtener sesión: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener sesión: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping
    public void registrar(@RequestBody Sesion sesion) {
        sesionService.registrar(sesion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> cerrarSesion(@PathVariable("id") Integer id) {
        try {
            logger.info("Recibida solicitud para cerrar sesión ID: {}", id);
            Sesion sesion = sesionService.cerrarSesion(id);
            logger.info("Sesión cerrada exitosamente: {}", sesion);
            return ResponseEntity.ok(sesion);
        } catch (Exception e) {
            logger.error("Error al cerrar sesión: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al cerrar sesión: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/usuario/{usuarioId}/cerrar-todas")
    public ResponseEntity<?> cerrarTodasLasSesiones(@PathVariable("usuarioId") Integer usuarioId) {
        try {
            logger.info("Recibida solicitud para cerrar todas las sesiones del usuario: {}", usuarioId);
            sesionService.cerrarTodasLasSesiones(usuarioId);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Todas las sesiones han sido cerradas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al cerrar todas las sesiones: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al cerrar todas las sesiones: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/check")
    public ResponseEntity<?> verificarSesion(@RequestParam String token) {
        try {
            boolean esValida = sesionService.verificarSesion(token);
            if (esValida) {
                return ResponseEntity.ok("Session is active");
            } else {
                return ResponseEntity.ok("Session is not active");
            }
        } catch (Exception e) {
            logger.error("Error al verificar sesión: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al verificar sesión: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}