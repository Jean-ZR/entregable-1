package com.jczap.controller;

import com.jczap.modelo.Permiso;
import com.jczap.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@CrossOrigin(origins = "*")
public class PermisoController {
    
    @Autowired
    private PermisoService permisoService;
    
    @GetMapping
    public List<Permiso> listarTodos() {
        return permisoService.listarActivos();
    }
}