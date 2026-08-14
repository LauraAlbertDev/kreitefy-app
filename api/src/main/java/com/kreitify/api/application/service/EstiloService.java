package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.EstiloDto;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Estilo;
import com.kreitify.api.domain.persistence.BasePersistence;
import org.springframework.stereotype.Service;

@Service
public class EstiloService extends BaseService<Estilo, EstiloDto, Long>{
    protected EstiloService(BasePersistence<Estilo, Long> persistence, EntityMapper<EstiloDto, Estilo> mapper) {
        super(persistence, mapper);
    }
}
