package com.jczap.dto;

import java.util.List;

public class RolRequest {

    private String nombre;
    private String descripcion;
    private Boolean activo;
    private List<Integer> permisoIds; // Asumimos que los permisos son identificados por su ID

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public List<Integer> getPermisoIds() {
        return permisoIds;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setPermisoIds(List<Integer> permisoIds) {
        this.permisoIds = permisoIds;
    }
}
