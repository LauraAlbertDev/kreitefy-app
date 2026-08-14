package com.kreitify.api.domain.persistence;

import com.kreitify.api.domain.entity.Estilo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstiloPersistence extends BasePersistence<Estilo, Long>, JpaRepository<Estilo, Long> {
    Optional<Estilo> findByNombre(String nombre);
}
