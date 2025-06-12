package com.jczap.service;
import com.jczap.dto.EmpleadoStatsDTO;
import com.jczap.modelo.Empleado;
import com.jczap.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmpleadoService {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoService.class);
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }
    
    public Empleado obtenerPorId(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }
    
    public Empleado registrar(Empleado empleado) throws Exception {
        // Validaciones
        if (empleadoRepository.findByDni(empleado.getDni()).isPresent()) {
            throw new Exception("Ya existe un empleado con ese DNI");
        }
        if (empleadoRepository.findByEmail(empleado.getEmail()).isPresent()) {
            throw new Exception("Ya existe un empleado con ese email");
        }

        // Establecer valores por defecto
        empleado.setEstado("ACTIVO");
        empleado.setFechaIngreso(LocalDate.now());
        empleado.setFechaCreacion(LocalDateTime.now());
        empleado.setFechaModificacion(LocalDateTime.now());

        return empleadoRepository.save(empleado);
    }
    
    public Empleado actualizar(Empleado empleado) throws Exception {
        // Validar existencia
        Empleado empleadoExistente = empleadoRepository.findById(empleado.getId())
            .orElseThrow(() -> new Exception("Empleado no encontrado"));

        // Validar DNI único
        empleadoRepository.findByDni(empleado.getDni())
            .ifPresent(e -> {
                if (!e.getId().equals(empleado.getId())) {
                    throw new RuntimeException("Ya existe un empleado con ese DNI");
                }
            });

        // Actualizar fecha de modificación
        empleado.setFechaModificacion(LocalDateTime.now());
        
        return empleadoRepository.save(empleado);
    }
    
    public void eliminar(Integer id) throws Exception {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new Exception("Empleado no encontrado"));
        
        empleado.setEstado("INACTIVO");
        empleado.setFechaModificacion(LocalDateTime.now());
        empleadoRepository.save(empleado);
    }
    
    public EmpleadoStatsDTO obtenerEstadisticas() {
        List<Empleado> empleados = empleadoRepository.findAll();
        logger.info("Total de empleados encontrados: {}", empleados.size());
        
        long totalEmpleados = empleados.size();
        long empleadosActivos = empleados.stream()
            .filter(e -> e != null && "ACTIVO".equalsIgnoreCase(e.getEstado()))
            .count();
        long empleadosInactivos = totalEmpleados - empleadosActivos;
        
        logger.info("Empleados activos: {}, inactivos: {}", empleadosActivos, empleadosInactivos);
        
        BigDecimal salarioPromedio = empleados.stream()
            .filter(e -> e != null && e.getSalario() != null)
            .map(Empleado::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(new BigDecimal(totalEmpleados > 0 ? totalEmpleados : 1), 2, RoundingMode.HALF_UP);
        
        logger.info("Salario promedio: {}", salarioPromedio);
        
        EmpleadoStatsDTO stats = new EmpleadoStatsDTO(
            totalEmpleados,
            empleadosActivos,
            empleadosInactivos,
            salarioPromedio.doubleValue()
        );
        
        logger.info("Estadísticas generadas: {}", stats);
        return stats;
    }
}