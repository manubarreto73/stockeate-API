package com.stockify.api.sesiones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockify.api.sesiones.entity.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    List<Usuario> findByActivoTrue();

    Optional<Usuario> findByIdAndActivoTrue(Long id);

    Optional<Usuario> findByEmailAndActivoTrue(String email);

    boolean existsByEmailAndActivoTrue(String email);

}