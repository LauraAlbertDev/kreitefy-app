package com.kreitify.api.domain.persistence;

import com.kreitify.api.domain.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaPersistence extends BasePersistence<Artista, Long>, JpaRepository<Artista, Long> {
    Optional<Artista> findByNombre(String nombre);
}
