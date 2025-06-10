package com.jczap.service;

import com.jczap.modelo.Rol;
import com.jczap.modelo.Sesion;
import com.jczap.modelo.Usuario;
import com.jczap.repository.RolRepository;
import com.jczap.repository.SesionRepository;
import com.jczap.repository.UsuarioRepository;
import com.jczap.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private RolRepository rolRepository;

    public Sesion login(String username, String password, String ip, String userAgent) throws Exception {
        System.out.println("Intento de login para usuario: " + username);

        if (username == null || username.trim().isEmpty()) {
            throw new Exception("El nombre de usuario no puede estar vacío");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario no encontrado en la base de datos: " + username);
            throw new Exception("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        System.out.println("Usuario encontrado: " + usuario.getUsername());

        if (!PasswordUtils.checkPassword(password, usuario.getPassword())) {
            System.out.println("Contraseña incorrecta para usuario: " + username);
            throw new Exception("Contraseña incorrecta");
        }

        // Crear sesión
        Sesion sesion = new Sesion();
        sesion.setUsuario(usuario);
        sesion.setToken(UUID.randomUUID().toString());
        sesion.setFechaInicio(LocalDateTime.now());
        sesion.setFechaExpiracion(LocalDateTime.now().plusHours(4));
        sesion.setIpAddress(ip);
        sesion.setUserAgent(userAgent);
        sesion.setActiva(true);

        Sesion sesionGuardada = sesionRepository.save(sesion);
        System.out.println("Sesión creada exitosamente para usuario: " + username);

        return sesionGuardada;
    }

    public boolean validarToken(String token) {
        // Eliminar el prefijo "Bearer " si existe
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Optional<Sesion> sesionOpt = sesionRepository.findByToken(token);
        return sesionOpt.isPresent() && sesionOpt.get().isValid();
    }

    public Usuario register(String username, String password, String email, Integer rolId) throws Exception {
        // Validar que el username no exista
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new Exception("El nombre de usuario ya existe");
        }

        // Validar que el email no exista
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new Exception("El email ya está registrado");
        }

        // Obtener el rol
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new Exception("Rol no encontrado"));

        // Crear el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(PasswordUtils.hashPassword(password));
        usuario.setEmail(email);
        usuario.setRol(rol);
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        // Guardar y retornar el usuario
        return usuarioRepository.save(usuario);
    }

    public void logout(String token) {
        Optional<Sesion> sesionOpt = sesionRepository.findByToken(token);
        if (sesionOpt.isPresent()) {
            Sesion sesion = sesionOpt.get();
            sesion.setActiva(false);
            sesion.setFechaFin(LocalDateTime.now());
            sesionRepository.save(sesion);
        }
    }
}
