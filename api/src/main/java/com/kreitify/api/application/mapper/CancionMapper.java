package com.kreitify.api.application.mapper;
import com.kreitify.api.application.dto.CancionCreateDto;
import com.kreitify.api.application.dto.CancionDto;
import com.kreitify.api.application.dto.CancionSimpleDto;
import com.kreitify.api.domain.entity.Album;
import com.kreitify.api.domain.entity.Artista;
import com.kreitify.api.domain.entity.Cancion;
import com.kreitify.api.domain.entity.Estilo;
import com.kreitify.api.domain.persistence.AlbumPersistence;
import com.kreitify.api.domain.persistence.ArtistaPersistence;
import com.kreitify.api.domain.persistence.EstiloPersistence;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public abstract class CancionMapper implements EntityMapper<CancionDto, Cancion> {
    @Autowired protected ArtistaPersistence artistaPersistence;
    @Autowired protected EstiloPersistence estiloPersistence;
    @Autowired protected AlbumPersistence albumPersistence;

    @Override
    @Mapping(source = "artista.nombre", target = "artista")
    @Mapping(source = "estilo.nombre", target = "estilo")
    @Mapping(source = "album.titulo", target = "album")
    @Mapping(source = "album.imagen", target = "imagenAlbum")
    public abstract  CancionDto toDto(Cancion cancion);

    @Mapping(target = "artista", ignore = true)
    @Mapping(target = "estilo", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "reproducciones", constant = "0L")
    public abstract Cancion toEntity(CancionDto dto);

    @Override
    @Mapping(target = "artista", ignore = true)
    @Mapping(target = "estilo", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "valoracion", ignore = true)
    @Mapping(target = "reproducciones", ignore = true)
    public abstract void updateEntityFromDto(CancionDto dto, @MappingTarget Cancion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reproducciones", constant = "0L")
    @Mapping(target = "valoracion", ignore = true)
    @Mapping(target = "titulo", source = "dto.titulo")
    @Mapping(target = "artista", source = "artista")
    @Mapping(target = "estilo", source = "estilo")
    @Mapping(target = "album", source = "album")
    public abstract Cancion toEntity(CancionCreateDto dto, Artista artista, Estilo estilo, Album album);

    @Mapping(source = "artista.nombre", target = "artista")
    @Mapping(source = "album.imagen", target = "imagenAlbum")
    public abstract CancionSimpleDto toSimpleDto(Cancion entity);

    public Cancion fromId(Long id) {
        if (id == null) return null;
        Cancion c = new Cancion();
        c.setId(id);
        return c;
    }

    @AfterMapping
    protected void resolveRelaciones(CancionDto dto, @MappingTarget Cancion cancion) {
        cancion.setArtista(resolve(dto.getArtista(), artistaPersistence::findByNombre, "Artista"));
        cancion.setEstilo(resolve(dto.getEstilo(), estiloPersistence::findByNombre, "Estilo"));

        Album album = resolve(dto.getAlbum(), albumPersistence::findFirstByTitulo, "Álbum");
        if (album != null) {
            if (isPresent(dto.getImagenAlbum())) {
                album.setImagen(dto.getImagenAlbum().trim());
            }
            cancion.setAlbum(album);
        }
    }

    private <T> T resolve(String valor, Function<String, Optional<T>> finder, String errorName) {
        if (!isPresent(valor)) return null;
        return finder.apply(valor.trim())
                .orElseThrow(() -> new RuntimeException(errorName + " no encontrado: " + valor));
    }

    private boolean isPresent(String s) {
        return s != null && !s.isBlank();
    }
}
