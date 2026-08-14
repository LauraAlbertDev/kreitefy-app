package com.kreitify.api.infraestructure.rest;

import com.kreitify.api.application.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

public abstract class BaseRestController<
        D,
        ID,
        S extends BaseService<?, D, ID>
        > {

    protected final S service;

    protected BaseRestController(S service) {
        this.service = service;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<Page<D>> getAll(
            @PageableDefault(size = 10, page = 0)
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/all/unpaged")
    public ResponseEntity<Page<D>> getAllUnpaged(
    ) {
        return ResponseEntity.ok(service.findAll(Pageable.unpaged()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PostMapping(consumes = "application/json")
    public ResponseEntity<D> create(@Valid @RequestBody D dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable ID id) {
        D dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PutMapping(
            value = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<D> update(
            @PathVariable ID id,
            @Valid @RequestBody D dto
    ) {
        D updated = service.update(id, dto);

        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}