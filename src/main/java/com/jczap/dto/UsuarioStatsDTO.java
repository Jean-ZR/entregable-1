package com.jczap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// import lombok.Builder; // Si quieres usar Builder, descomenta esta línea

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// @Builder // Si quieres usar el patrón builder, descomenta esta línea
public class UsuarioStatsDTO {

    @JsonProperty("totalUsers")
    private long totalUsuarios;

    @JsonProperty("activeUsers")
    private long usuariosActivos;

    @JsonProperty("inactiveUsers")
    private long usuariosInactivos;

    // Constructor manual si prefieres evitar depender solo de Lombok
    public UsuarioStatsDTO(long totalUsuarios, long usuariosActivos, long usuariosInactivos) {
        this.totalUsuarios = totalUsuarios;
        this.usuariosActivos = usuariosActivos;
        this.usuariosInactivos = usuariosInactivos;
    }

    @Override
    public String toString() {
        return String.format(
            "UsuarioStatsDTO{totalUsuarios=%d, usuariosActivos=%d, usuariosInactivos=%d}",
            totalUsuarios, usuariosActivos, usuariosInactivos
        );
    }
}
