package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.ArtistaDto;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Artista;
import com.kreitify.api.domain.persistence.BasePersistence;
import org.springframework.stereotype.Service;

@Service
public class ArtistaService extends BaseService<Artista, ArtistaDto, Long>{
    protected ArtistaService(BasePersistence<Artista, Long> persistence, EntityMapper<ArtistaDto, Artista> mapper) {
        super(persistence, mapper);
    }
}
