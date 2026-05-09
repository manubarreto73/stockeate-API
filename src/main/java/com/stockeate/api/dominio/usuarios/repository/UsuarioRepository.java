package com.stockeate.api.dominio.usuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockeate.api.dominio.negocios.entities.Negocio;
import com.stockeate.api.dominio.usuarios.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    List<Usuario> findAll();

    List<Usuario> findByNegocioAndActivoTrue(Negocio negocio);
    
    List<Usuario> findByActivoTrue();

    Optional<Usuario> findByIdAndNegocioAndActivoTrue(Long id, Negocio negocio);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.negocio WHERE u.email = :email AND u.activo = true")
    Optional<Usuario> findByEmailAndActivoTrue(@Param("email") String email);

    boolean existsByEmail(String email);

}