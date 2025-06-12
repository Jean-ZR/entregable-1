package com.jczap.controller;

import com.jczap.dto.EmpleadoStatsDTO;
import com.jczap.modelo.Empleado;
import com.jczap.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
    
    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    public List<Empleado> listarTodos() {
        return empleadoService.listarTodos();
    }
    
    @GetMapping("/stats")
    public ResponseEntity<?> obtenerEstadisticas() {
        try {
            logger.info("Solicitando estadísticas de empleados");
            EmpleadoStatsDTO stats = empleadoService.obtenerEstadisticas();
            logger.info("Estadísticas obtenidas: {}", stats);
            
            if (stats == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No se pudieron obtener las estadísticas");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Error al obtener estadísticas: {}", e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener estadísticas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable("id") Integer id) {
        return empleadoService.obtenerPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = empleadoService.registrar(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Integer id, @RequestBody Empleado empleado) {
        try {
            empleado.setId(id);
            Empleado empleadoActualizado = empleadoService.actualizar(empleado);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            empleadoService.eliminar(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Empleado eliminado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}