package com.kreitify.api.application.mapper;

import com.kreitify.api.application.dto.AlbumDto;
import com.kreitify.api.domain.entity.Album;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumMapper extends EntityMapper<AlbumDto, Album>{
}
