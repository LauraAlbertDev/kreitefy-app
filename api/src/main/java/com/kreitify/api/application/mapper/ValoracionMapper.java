package com.kreitify.api.application.mapper;

import com.kreitify.api.application.dto.ValoracionDto;
import com.kreitify.api.domain.entity.Valoracion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CancionMapper.class, UsuarioMapper.class})
public interface ValoracionMapper extends EntityMapper<ValoracionDto, Valoracion> {

    @Override
    @Mapping(source = "cancion.id", target = "cancionId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    ValoracionDto toDto(Valoracion entity);

    @Override
    @Mapping(source = "cancionId", target = "cancion")
    @Mapping(source = "usuarioId", target = "usuario")
    Valoracion toEntity(ValoracionDto dto);

    @Override
    @Mapping(target = "cancion", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntityFromDto(ValoracionDto dto, @MappingTarget Valoracion entity);
}