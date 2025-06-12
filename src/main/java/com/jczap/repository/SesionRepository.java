package com.jczap.repository;

import com.jczap.modelo.Sesion;
import com.jczap.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {
    Optional<Sesion> findByToken(String token);
    List<Sesion> findByUsuario(Usuario usuario);
    List<Sesion> findByUsuarioId(Integer usuarioId);
}
