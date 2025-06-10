package com.jczap.repository;

import com.jczap.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByDni(String dni);
    Optional<Empleado> findByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    List<Empleado> findByEstado(String estado);
}
