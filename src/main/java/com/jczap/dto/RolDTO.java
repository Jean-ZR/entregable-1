package com.jczap.dto;

public class RolDTO {

    private Long id;
    private String nombre;

    // Constructor vacío
    public RolDTO() {}

    // Constructor completo
    public RolDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
