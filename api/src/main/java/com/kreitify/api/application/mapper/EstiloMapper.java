package com.kreitify.api.application.mapper;

import com.kreitify.api.application.dto.EstiloDto;
import com.kreitify.api.domain.entity.Estilo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstiloMapper  extends EntityMapper<EstiloDto, Estilo>{
}
