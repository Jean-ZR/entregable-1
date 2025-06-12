package com.jczap.dto;

import java.util.Set;

public class RolResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Set<PermisoResponse> permisos;

    // Setters y Getters manualmente
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<PermisoResponse> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<PermisoResponse> permisos) {
        this.permisos = permisos;
    }

    // Clase estática PermisoResponse
    public static class PermisoResponse {
        private Integer id;
        private String nombre;
        private String codigo;
        private String descripcion;
        private String categoria;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }
    }
}
