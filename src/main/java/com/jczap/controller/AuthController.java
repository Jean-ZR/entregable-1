package com.jczap.controller;

import com.jczap.modelo.Sesion;
import com.jczap.modelo.Usuario;
import com.jczap.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Sesion login(
        @RequestBody Map<String, String> request,
        @RequestHeader(value = "User-Agent", required = true) String userAgent,
        @RequestHeader(value = "X-Forwarded-For", required = false) String ipAddress
    ) throws Exception {
        String username = request.get("username");
        String password = request.get("password");
        String ip = (ipAddress != null) ? ipAddress : "127.0.0.1"; // Fallback

        return authService.login(username, password, ip, userAgent);
    }

    @GetMapping("/validar/{token}")
    public boolean validarToken(@PathVariable("token") String token) {
        return authService.validarToken(token);
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Map<String, String> request) throws Exception {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");
        Integer rolId = Integer.parseInt(request.get("rolId"));

        return authService.register(username, password, email, rolId);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Sesión cerrada exitosamente");
        return ResponseEntity.ok(response);
    }
}
