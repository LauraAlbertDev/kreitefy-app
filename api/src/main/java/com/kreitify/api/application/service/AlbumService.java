package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.AlbumDto;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Album;
import com.kreitify.api.domain.persistence.BasePersistence;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends BaseService<Album, AlbumDto, Long>{
    protected AlbumService(BasePersistence<Album, Long> persistence, EntityMapper<AlbumDto, Album> mapper) {
        super(persistence, mapper);
    }
}
