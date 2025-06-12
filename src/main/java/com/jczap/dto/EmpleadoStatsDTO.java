package com.jczap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoStatsDTO {
    @JsonProperty("totalEmpleados")
    private long totalEmpleados;
    
    @JsonProperty("empleadosActivos")
    private long empleadosActivos;
    
    @JsonProperty("empleadosInactivos")
    private long empleadosInactivos;
    
    @JsonProperty("salarioPromedio")
    private double salarioPromedio;
    
    public EmpleadoStatsDTO(long totalEmpleados, long empleadosActivos, long empleadosInactivos, double salarioPromedio) {
        this.totalEmpleados = totalEmpleados;
        this.empleadosActivos = empleadosActivos;
        this.empleadosInactivos = empleadosInactivos;
        this.salarioPromedio = salarioPromedio;
    }
    
    @Override
    public String toString() {
        return String.format(
            "EmpleadoStatsDTO{totalEmpleados=%d, empleadosActivos=%d, empleadosInactivos=%d, salarioPromedio=%.2f}",
            totalEmpleados, empleadosActivos, empleadosInactivos, salarioPromedio
        );
    }
} 