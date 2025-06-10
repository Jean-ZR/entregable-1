package com.jczap.repository;

import com.jczap.modelo.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    List<Permiso> findByCodigoIn(List<String> codigos);
    List<Permiso> findByActivoTrue();
}