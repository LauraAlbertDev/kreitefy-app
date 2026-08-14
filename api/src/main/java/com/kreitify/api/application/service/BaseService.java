package com.kreitify.api.application.service;

import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.exception.EntityInUseException;
import com.kreitify.api.domain.persistence.BasePersistence;
import com.kreitify.api.domain.persistence.CancionPersistence;
import com.kreitify.api.domain.validation.DeleteValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseService<T, D, ID> {

    protected final BasePersistence<T, ID> persistence;
    protected final EntityMapper<D, T> mapper;
    @Autowired
    private List<DeleteValidator> deleteValidators;
    protected BaseService(
            BasePersistence<T, ID> persistence,
            EntityMapper<D, T> mapper
    ) {
        this.persistence = persistence;
        this.mapper = mapper;
    }

    protected T findByIdOrThrow(ID id) {
        return persistence.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Registro no encontrado con ID: " + id));
    }

    protected void preValidation(D dto) {}
    protected void preDeleteValidation(T entity) {}

    @Transactional(readOnly = true)
    public Page<D> findAll(Pageable pageable) {
        return persistence.findAll(pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public D save(D dto) {
        System.out.println("DEBUG: Entrando al método save del BaseService con el DTO: " + dto);
        preValidation(dto);
        T entity = mapper.toEntity(dto);
        T savedEntity = persistence.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public D findById(ID id) {
        return mapper.toDto(findByIdOrThrow(id));
    }

    public D update(ID id, D dto) {
        T entity = findByIdOrThrow(id);
        preValidation(dto);
        mapper.updateEntityFromDto(dto, entity);
        T updated = persistence.save(entity);
        return mapper.toDto(updated);
    }

    @Transactional
    public void deleteById(ID id) {
        T entity = findByIdOrThrow(id);

        if (deleteValidators != null) {
            for (DeleteValidator validator : deleteValidators) {
                validator.validate(entity);
            }
        }

        preDeleteValidation(entity);
        persistence.delete(entity);
    }

    private <E> Specification<E> createSongsCheckSpecification(String fieldName, ID id) {
        return (root, query, cb) -> cb.equal(root.get(fieldName).<Object>get("id"), id);
    }
}