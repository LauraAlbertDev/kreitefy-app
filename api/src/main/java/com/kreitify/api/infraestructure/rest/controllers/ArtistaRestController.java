package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.dto.ArtistaDto;
import com.kreitify.api.application.mapper.ArtistaMapper;
import com.kreitify.api.application.service.ArtistaService;
import com.kreitify.api.domain.persistence.ArtistaPersistence;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/artistas", "/artista"})
public class ArtistaRestController extends BaseRestController<ArtistaDto, Long, ArtistaService> {
    public ArtistaRestController(ArtistaService service) {
        super(service);
    }
}