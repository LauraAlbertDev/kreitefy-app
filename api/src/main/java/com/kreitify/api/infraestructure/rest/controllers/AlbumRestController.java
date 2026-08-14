package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.dto.AlbumDto;
import com.kreitify.api.application.service.AlbumService;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/albums", "/album"})
public class AlbumRestController
        extends BaseRestController<AlbumDto, Long, AlbumService> {

    public AlbumRestController(AlbumService service) {
        super(service);
    }
}