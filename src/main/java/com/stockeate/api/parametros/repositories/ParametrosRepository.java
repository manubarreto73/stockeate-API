package com.stockeate.api.parametros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.parametros.entities.Parametros;

public interface ParametrosRepository extends JpaRepository<Parametros, Long> {
    
    Optional<Parametros> findById(Long id);

}
