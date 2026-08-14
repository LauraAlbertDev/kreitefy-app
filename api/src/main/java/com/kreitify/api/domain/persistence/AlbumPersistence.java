package com.kreitify.api.domain.persistence;

import com.kreitify.api.domain.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumPersistence extends BasePersistence<Album, Long>, JpaRepository<Album, Long> {
    Optional<Album> findFirstByTitulo(String nombre);
}
