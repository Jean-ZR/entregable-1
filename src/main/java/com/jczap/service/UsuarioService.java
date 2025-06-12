package com.jczap.service;

import com.jczap.dto.UsuarioStatsDTO;
import com.jczap.modelo.Usuario;
import com.jczap.modelo.Rol;
import com.jczap.repository.UsuarioRepository;
import com.jczap.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario registrar(Usuario usuario) throws Exception {
        // Validaciones básicas
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new Exception("El nombre de usuario es requerido");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new Exception("La contraseña es requerida");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new Exception("El email es requerido");
        }

        // Verificar y obtener el rol existente
        if (usuario.getRol() != null && usuario.getRol().getId() != null) {
            Rol rol = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new Exception("Rol no encontrado"));
            usuario.setRol(rol);
        } else {
            throw new Exception("Debe especificar un rol válido");
        }

        // Verificar usuario único
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new Exception("El nombre de usuario ya existe");
        }

        // Verificar email único
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new Exception("El email ya está registrado");
        }

        // Encriptar contraseña
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));

        // Establecer fechas
        usuario.setFechaCreacion(LocalDateTime.now());

        // Guardar y retornar el usuario
        return usuarioRepository.save(usuario);
    }

    public void actualizar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioStatsDTO obtenerEstadisticas() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.info("Total de usuarios encontrados: {}", usuarios.size());
        
        long totalUsuarios = usuarios.size();
        long usuariosActivos = usuarios.stream()
            .filter(u -> u != null && Boolean.TRUE.equals(u.getActivo()))
            .count();
        long usuariosInactivos = totalUsuarios - usuariosActivos;
        
        logger.info("Usuarios activos: {}, inactivos: {}", usuariosActivos, usuariosInactivos);
        
        UsuarioStatsDTO stats = new UsuarioStatsDTO(
            totalUsuarios,
            usuariosActivos,
            usuariosInactivos
        );
        
        logger.info("Estadísticas generadas: {}", stats);
        return stats;
    }
}
