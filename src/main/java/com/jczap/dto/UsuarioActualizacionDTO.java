package com.jczap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioActualizacionDTO {
    private String username;
    
    @Email(message = "El email debe ser válido")
    private String email;
    
    private Boolean activo;
    
    private RolDTO rol;
    
    public static class RolDTO {
        private Integer id;
        
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
        }
    }
    
    // Getters y Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public RolDTO getRol() {
        return rol;
    }
    
    public void setRol(RolDTO rol) {
        this.rol = rol;
    }
} 