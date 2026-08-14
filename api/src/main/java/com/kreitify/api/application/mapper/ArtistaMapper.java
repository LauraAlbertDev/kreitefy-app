package com.kreitify.api.application.mapper;

import com.kreitify.api.application.dto.ArtistaDto;
import com.kreitify.api.domain.entity.Artista;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistaMapper extends EntityMapper<ArtistaDto, Artista>{
}
