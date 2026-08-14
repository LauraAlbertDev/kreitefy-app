package com.kreitify.api.domain.persistence;

import com.kreitify.api.application.dto.CancionSimpleDto;
import com.kreitify.api.domain.entity.Cancion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancionPersistence extends BasePersistence<Cancion, Long>, JpaRepository<Cancion, Long>, JpaSpecificationExecutor<Cancion> {
    @Query("""
                SELECT new com.kreitify.api.application.dto.CancionSimpleDto(
                    c.id,
                    c.titulo,
                    a.nombre,
                    al.imagen
                )
                FROM Cancion c
                JOIN c.artista a
                JOIN c.album al
                WHERE (:idEstilo IS NULL OR c.estilo.id = :idEstilo)
                ORDER BY c.fecha DESC
            """)
    List<CancionSimpleDto> findTop5Recientes(Long idEstilo, Pageable pageable);
    @Query("""
                SELECT new com.kreitify.api.application.dto.CancionSimpleDto(
                    c.id,
                    c.titulo,
                    a.nombre,
                    al.imagen
                )
                FROM Cancion c
                JOIN c.artista a
                JOIN c.album al
                WHERE (:idEstilo IS NULL OR c.estilo.id = :idEstilo)
                ORDER BY c.reproducciones DESC
            """)
    List<CancionSimpleDto> findTop5Hits(Long idEstilo, Pageable pageable);

    @Query("""
    SELECT c.estilo.id
    FROM Reproduccion r
    JOIN r.cancion c
    WHERE r.usuario.id = :usuarioId
    GROUP BY c.estilo.id
    ORDER BY COUNT(r.id) DESC
""")
    List<Long> findTop2EstilosByUsuario(Long usuarioId, Pageable pageable);

    @Query("""
    SELECT new com.kreitify.api.application.dto.CancionSimpleDto(
        c.id,
        c.titulo,
        a.nombre,
        al.imagen
    )
    FROM Reproduccion r
    JOIN r.cancion c
    JOIN c.artista a
    JOIN c.album al
    WHERE r.usuario.id = :usuarioId
      AND c.estilo.id IN :estilosIds
      AND c.valoracion > 3
    GROUP BY c.id, c.titulo, a.nombre, al.imagen
    ORDER BY COUNT(r.id) DESC
""")
    List<CancionSimpleDto> findForYouSongs(
            Long usuarioId,
            List<Long> estilosIds,
            Pageable pageable
    );

    @Query("""
    SELECT c.estilo.nombre
    FROM Reproduccion r
    JOIN r.cancion c
    WHERE r.usuario.id = :usuarioId
    GROUP BY c.estilo.id, c.estilo.nombre
    ORDER BY COUNT(r.id) DESC
""")
    List<String> findTop2EstiloNombres(Long usuarioId, Pageable pageable);
}

