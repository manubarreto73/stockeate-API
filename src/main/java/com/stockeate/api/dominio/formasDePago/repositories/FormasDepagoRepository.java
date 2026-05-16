package com.stockeate.api.dominio.formasDePago.repositories;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stockeate.api.dominio.formasDePago.entities.FormaDePago;

public interface FormasDepagoRepository extends JpaRepository<FormaDePago, Long> {
    
    Page<FormaDePago> findAll(Pageable pageable);

    Optional<FormaDePago> findById(Long id);

}
