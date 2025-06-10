package com.jczap.controller;

import com.jczap.modelo.Rol;
import com.jczap.dto.RolRequest;
import com.jczap.dto.RolResponse;
import com.jczap.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {
    
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public List<RolResponse> listarTodos() {
        return rolService.listarTodos().stream()
            .map(rolService::toResponse)
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> obtenerPorId(@PathVariable("id") Integer id) {
        Rol rol = rolService.obtenerPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rolService.toResponse(rol));
    }
    
    @PostMapping
    public ResponseEntity<RolResponse> registrar(@RequestBody RolRequest rolRequest) {
        try {
            Rol rol = rolService.registrar(rolRequest);
            return ResponseEntity.ok(rolService.toResponse(rol));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RolResponse> actualizar(@PathVariable("id") Integer id, @RequestBody RolRequest rolRequest) {
        try {
            Rol rol = rolService.actualizar(id, rolRequest);
            return ResponseEntity.ok(rolService.toResponse(rol));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        try {
            rolService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}