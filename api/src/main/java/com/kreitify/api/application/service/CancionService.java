package com.kreitify.api.application.service;

import com.kreitify.api.application.CancionCriteria;
import com.kreitify.api.application.dto.CancionDto;
import com.kreitify.api.application.dto.CancionSimpleDto;
import com.kreitify.api.application.mapper.CancionMapper;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Album;
import com.kreitify.api.domain.entity.Artista;
import com.kreitify.api.domain.entity.Cancion;
import com.kreitify.api.domain.persistence.BasePersistence;
import com.kreitify.api.domain.persistence.CancionPersistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CancionService extends BaseService<Cancion, CancionDto, Long>{
    @PersistenceContext
    private EntityManager entityManager;
    private final CancionPersistence cancionPersistence;
    private final CancionMapper cancionMapper;

    protected CancionService(BasePersistence<Cancion, Long> persistence, EntityMapper<CancionDto, Cancion> mapper, CancionPersistence cancionPersistence, CancionMapper cancionMapper) {
        super(persistence, mapper);
        this.cancionPersistence = cancionPersistence;
        this.cancionMapper = cancionMapper;
    }


    public void registrarReproduccion(Long id) {
        Cancion cancion = persistence.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con id: " + id));
        cancion.setReproducciones(cancion.getReproducciones() + 1);
    }

    public List<CancionSimpleDto> getTop5Recientes(CancionCriteria criteria) {
        Long idEstilo = criteria != null
                ? criteria.getIdEstilo()
                : null;

        return this.cancionPersistence
                .findTop5Recientes(idEstilo, PageRequest.of(0, 5));
    }

    public List<CancionSimpleDto> getTop5Hits(CancionCriteria criteria) {
        Long idEstilo = criteria != null
                ? criteria.getIdEstilo()
                : null;

        return this.cancionPersistence
                .findTop5Hits(idEstilo, PageRequest.of(0, 5));
    }

    public Page<CancionSimpleDto> findAllFilters(CancionCriteria criteria, Pageable pageable) {
        return this.persistence.findAll(createSpecification(criteria, null, null), pageable)
                .map(this.cancionMapper::toSimpleDto);
    }

    public List<String> getSugerencias(CancionCriteria criteria, String term, String campo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Cancion> root = query.from(Cancion.class);
        Path<String> pathSelect;
        if ("artista.nombre".equals(campo)) {
            pathSelect = root.join("artista", JoinType.LEFT).get("nombre");
        } else if ("album.titulo".equals(campo)) {
            pathSelect = root.join("album", JoinType.LEFT).get("titulo");
        } else {
            pathSelect = root.get("titulo");
        }
        query.select(pathSelect).distinct(true);
        query.orderBy(cb.asc(pathSelect));
        Specification<Cancion> spec = createSpecification(criteria, term, campo);
        Predicate predicate = spec.toPredicate(root, query, cb);
        if (predicate != null) query.where(predicate);
        return entityManager.createQuery(query)
                .setMaxResults(10)
                .getResultList();
    }

    private Specification<Cancion> createSpecification(CancionCriteria criteria, String term, String campoSugerencia) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Cancion, Artista> artistaJoin = root.join("artista", JoinType.LEFT);
            Join<Cancion, Album> albumJoin = root.join("album", JoinType.LEFT);

            if (criteria != null) {
                if (criteria.getIdEstilo() != null)
                    predicates.add(cb.equal(root.get("estilo").get("id"), criteria.getIdEstilo()));

                addLikePredicate(cb, predicates, root.get("titulo"), criteria.getTitulo());
                addLikePredicate(cb, predicates, artistaJoin.get("nombre"), criteria.getArtista());
                addLikePredicate(cb, predicates, albumJoin.get("titulo"), criteria.getAlbum());
            }

            if (isPresent(term) && isPresent(campoSugerencia)) {
                Path<String> path = switch (campoSugerencia) {
                    case "artista.nombre" -> artistaJoin.get("nombre");
                    case "album.titulo" -> albumJoin.get("titulo");
                    default -> root.get("titulo");
                };
                addLikePredicate(cb, predicates, path, term);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void addLikePredicate(CriteriaBuilder cb, List<Predicate> predicates, Path<String> path, String value) {
        if (isPresent(value)) {
            predicates.add(cb.like(cb.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }

    private boolean isPresent(String s) {
        return s != null && !s.isBlank();
    }

    public List<CancionSimpleDto> getTop5FYP(Long usuarioId) {
        List<Long> estilos = this.cancionPersistence
                .findTop2EstilosByUsuario(usuarioId, PageRequest.of(0, 2));
        return this.cancionPersistence
                .findForYouSongs(usuarioId, estilos, PageRequest.of(0, 5));
    }

    public List<String> getEstilosUsuario(Long usuarioId) {
        return this.cancionPersistence
                .findTop2EstiloNombres(usuarioId, PageRequest.of(0, 2));
    }
}
