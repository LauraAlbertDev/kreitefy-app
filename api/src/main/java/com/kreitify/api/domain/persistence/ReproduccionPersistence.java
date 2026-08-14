package com.kreitify.api.domain.persistence;

import com.kreitify.api.domain.entity.Reproduccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReproduccionPersistence extends BasePersistence<Reproduccion, Long>, JpaRepository<Reproduccion, Long> {
    List<Reproduccion> findByUsuarioId(Long usuarioId);
}
