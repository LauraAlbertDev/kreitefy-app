package com.kreitify.api.domain.persistence;

import com.kreitify.api.domain.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValoracionPersistence extends BasePersistence<Valoracion, Long>,
        JpaRepository<Valoracion, Long> {
    Optional<Valoracion> findFirstByCancionIdAndUsuarioId(Long cancionId, Long usuarioId);

}
