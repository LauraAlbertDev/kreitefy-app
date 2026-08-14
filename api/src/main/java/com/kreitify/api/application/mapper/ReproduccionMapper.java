package com.kreitify.api.application.mapper;

import com.kreitify.api.application.dto.ReproduccionDto;
import com.kreitify.api.domain.entity.Reproduccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CancionMapper.class, UsuarioMapper.class})
public interface ReproduccionMapper extends EntityMapper<ReproduccionDto, Reproduccion>{

    @Override
    @Mapping(source = "cancion.id", target = "cancionId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "cancion.titulo", target = "cancionTitulo")
    ReproduccionDto toDto(Reproduccion entity);


    @Override
    @Mapping(target = "cancion", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Reproduccion toEntity(ReproduccionDto dto);

    @Override
    @Mapping(target = "cancion", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntityFromDto(ReproduccionDto dto, @MappingTarget Reproduccion entity);
}
