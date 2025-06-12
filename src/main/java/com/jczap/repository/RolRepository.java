package com.jczap.repository;

import com.jczap.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
    
    @Query("SELECT r FROM Rol r LEFT JOIN FETCH r.permisos WHERE r.id = :id")
    Optional<Rol> findByIdWithPermisos(Integer id);
}
