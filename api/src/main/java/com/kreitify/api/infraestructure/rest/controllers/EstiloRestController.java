package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.dto.EstiloDto;
import com.kreitify.api.application.service.EstiloService;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/estilos", "/estilo"})
public class EstiloRestController extends BaseRestController<EstiloDto, Long, EstiloService> {
    public EstiloRestController(EstiloService service) {
        super(service);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<EstiloDto>> getAll() {
        Pageable pageable = Pageable.unpaged();
        return ResponseEntity.ok(service.findAll(pageable).getContent());
    }
}
