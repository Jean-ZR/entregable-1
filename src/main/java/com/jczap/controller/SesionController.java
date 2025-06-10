package com.jczap.controller;

import com.jczap.modelo.Sesion;
import com.jczap.service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin(origins = "*")
public class SesionController {
    
    @Autowired
    private SesionService sesionService;
    
    @GetMapping
    public List<Sesion> listarTodos() {
        return sesionService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public Sesion obtenerPorId(@PathVariable("id") Integer id) {
        return sesionService.obtenerPorId(id);
    }
    
    @PostMapping
    public void registrar(@RequestBody Sesion sesion) {
        sesionService.registrar(sesion);
    }
    
    @PutMapping("/{id}")
    public void actualizar(@PathVariable("id") Integer id, @RequestBody Sesion sesion) {
        sesion.setId(id);
        sesionService.actualizar(sesion);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        sesionService.eliminar(id);
    }
}