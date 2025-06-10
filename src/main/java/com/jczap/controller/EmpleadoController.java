package com.jczap.controller;

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

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    public List<Empleado> listarTodos() {
        return empleadoService.listarTodos();
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