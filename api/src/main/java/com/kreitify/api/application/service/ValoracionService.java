package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.ValoracionDto;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Valoracion;
import com.kreitify.api.domain.persistence.BasePersistence;
import com.kreitify.api.domain.persistence.ValoracionPersistence;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValoracionService extends BaseService<Valoracion, ValoracionDto, Long> {
    private final ValoracionPersistence valoracionPersistence;
    protected ValoracionService(BasePersistence<Valoracion, Long> persistence, EntityMapper<ValoracionDto, Valoracion> mapper, ValoracionPersistence valoracionPersistence){
        super(persistence,mapper);
        this.valoracionPersistence = valoracionPersistence;
    }

    public Optional<ValoracionDto> findValoracionDtoByCancionIdAndUsuarioId(Long cancionId, Long usuarioId) {
        Optional<Valoracion> valoracion = this.valoracionPersistence
                .findFirstByCancionIdAndUsuarioId(cancionId, usuarioId);

        return Optional.of(valoracion.map(mapper::toDto).orElseThrow(EntityNotFoundException::new));
    }
}
