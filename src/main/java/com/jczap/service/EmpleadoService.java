package com.jczap.service;
import com.jczap.modelo.Empleado;
import com.jczap.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpleadoService {
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
}