package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.ReproduccionDto;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Cancion;
import com.kreitify.api.domain.entity.Reproduccion;
import com.kreitify.api.domain.entity.Usuario;
import com.kreitify.api.domain.persistence.BasePersistence;
import com.kreitify.api.domain.persistence.ReproduccionPersistence;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReproduccionService extends BaseService<Reproduccion, ReproduccionDto, Long>{
    private final ReproduccionPersistence reproduccionPersistence;
    private final EntityManager entityManager;

    protected ReproduccionService(BasePersistence<Reproduccion, Long> persistence, EntityMapper<ReproduccionDto, Reproduccion> mapper, ReproduccionPersistence reproduccionPersistence, EntityManager entityManager) {
        super(persistence, mapper);
        this.reproduccionPersistence = reproduccionPersistence;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public ReproduccionDto save(ReproduccionDto dto) {
        Reproduccion entity = mapper.toEntity(dto);
        entity.setCancion(entityManager.getReference(Cancion.class, dto.getCancionId()));
        entity.setUsuario(entityManager.getReference(Usuario.class, dto.getUsuarioId()));
        return mapper.toDto(persistence.save(entity));
    }

    @Transactional(readOnly = true)
    public List<ReproduccionDto> findByUsuarioId(Long usuarioId) {
        return this.reproduccionPersistence.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
