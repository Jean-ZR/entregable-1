package com.jczap.controller;

import com.jczap.modelo.Rol;
import com.jczap.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> listarTodos() {
        return rolService.listarTodos();
    }

    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable("id") Integer id) {
        return rolService.obtenerPorId(id);
    }

    @PostMapping
    public void registrar(@RequestBody Rol rol) {
        rolService.registrar(rol);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable("id") Integer id, @RequestBody Rol rol) {
        rol.setId(id);
        rolService.actualizar(rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        rolService.eliminar(id);
    }
}