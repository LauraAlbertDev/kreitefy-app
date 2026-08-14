package com.kreitify.api.application.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface EntityMapper <D, E>{
    D toDto(E entity);
    E toEntity(D dto);
    List<D> toDto(List<E> entityList);
    List<E> toEntity(List<D> dtoList);
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(D dto, @MappingTarget E entity);
}
