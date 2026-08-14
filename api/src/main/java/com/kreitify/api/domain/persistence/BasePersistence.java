package com.kreitify.api.domain.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import jakarta.persistence.criteria.Path;
import java.util.Optional;

public interface BasePersistence<T, ID> extends JpaSpecificationExecutor<T> {
    Optional<T> findById(ID id);
    T save(T entity);
    Page<T> findAll(Pageable pageable);
    void delete(T entity);
}
