package com.jczap.controller;

import com.jczap.dto.UsuarioStatsDTO;
import com.jczap.modelo.Usuario;
import com.jczap.modelo.Rol;
import com.jczap.modelo.Empleado;
import com.jczap.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }
    
    @GetMapping("/stats")
    public ResponseEntity<?> obtenerEstadisticas() {
        try {
            logger.info("Solicitando estadísticas de usuarios");
            UsuarioStatsDTO stats = usuarioService.obtenerEstadisticas();
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
    public Usuario obtenerPorId(@PathVariable("id") Integer id) {
        return usuarioService.obtenerPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> request) {
        try {
            // Validaciones básicas
            Map<String, String> errores = new HashMap<>();
            
            String username = (String) request.get("username");
            String password = (String) request.get("password");
            String email = (String) request.get("email");
            
            // Validaciones más estrictas
            if (username == null || username.trim().length() < 3) {
                errores.put("username", "El nombre de usuario debe tener al menos 3 caracteres");
            }
            if (password == null || password.trim().length() < 6) {
                errores.put("password", "La contraseña debe tener al menos 6 caracteres");
            }
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                errores.put("email", "Email inválido");
            }

            // Validar rol
            @SuppressWarnings("unchecked")
            Map<String, Object> rolMap = (Map<String, Object>) request.get("rol");
            if (rolMap == null || rolMap.get("id") == null) {
                errores.put("rol", "El rol es requerido");
            }

            // Validar empleado
            @SuppressWarnings("unchecked")
            Map<String, Object> empleadoMap = (Map<String, Object>) request.get("empleado");
            if (empleadoMap != null) {
                // Validaciones adicionales para empleado
                if (empleadoMap.get("dni") == null || ((String)empleadoMap.get("dni")).trim().isEmpty()) {
                    errores.put("dni", "El DNI del empleado es requerido");
                }
                if (empleadoMap.get("nombres") == null || ((String)empleadoMap.get("nombres")).trim().isEmpty()) {
                    errores.put("nombres", "Los nombres del empleado son requeridos");
                }
                if (empleadoMap.get("apellidos") == null || ((String)empleadoMap.get("apellidos")).trim().isEmpty()) {
                    errores.put("apellidos", "Los apellidos del empleado son requeridos");
                }
            }

            if (!errores.isEmpty()) {
                return ResponseEntity.badRequest().body(errores);
            }

            // Crear el usuario
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setEmail(email);
            usuario.setActivo(true);

            // Establecer el rol
            Rol rol = new Rol();
            rol.setId(Integer.parseInt(rolMap.get("id").toString()));
            usuario.setRol(rol);

            // Procesar empleado si existe
            if (empleadoMap != null) {
                Empleado empleado = new Empleado();
                empleado.setNombres((String) empleadoMap.get("nombres"));
                empleado.setApellidos((String) empleadoMap.get("apellidos"));
                empleado.setDni((String) empleadoMap.get("dni"));
                empleado.setEmail((String) empleadoMap.get("email"));
                empleado.setTelefono((String) empleadoMap.get("telefono"));
                empleado.setDireccion((String) empleadoMap.get("direccion"));
                
                // Procesar fecha de ingreso
                empleado.setFechaIngreso(LocalDate.now());
                
                // Establecer estado por defecto
                empleado.setEstado("ACTIVO");
                
                usuario.setEmpleado(empleado);
            }

            Usuario nuevoUsuario = usuarioService.registrar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            response.put("error", "Bad Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PutMapping("/{id}")
    public void actualizar(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioService.actualizar(usuario);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        usuarioService.eliminar(id);
    }
}