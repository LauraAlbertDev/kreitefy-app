package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.CancionCriteria;
import com.kreitify.api.application.dto.CancionDto;
import com.kreitify.api.application.dto.CancionSimpleDto;
import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.service.AuthService;
import com.kreitify.api.application.service.CancionService;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/canciones", "/cancion"})
@Validated
public class CancionRestController extends BaseRestController<CancionDto, Long, CancionService> {
    private final AuthService authService;

    public CancionRestController(CancionService service, AuthService authService) {
        super(service);
        this.authService = authService;
    }

    @GetMapping(value = "/novedades", produces = "application/json")
    public ResponseEntity<List<CancionSimpleDto>> getTop5Recientes(
            @RequestParam(required = false, name = "estilo") Long idEstilo
    ) {

        CancionCriteria criteria = new CancionCriteria.CancionCriteriaBuilder()
                .withIdEstilo(idEstilo)
                .build();

        List<CancionSimpleDto> canciones = this.service.getTop5Recientes(criteria);

        return ResponseEntity.ok(canciones);
    }

    @GetMapping(value = "/hits", produces = "application/json")
    public ResponseEntity<List<CancionSimpleDto>> getTop5Hits(
            @RequestParam(required = false, name = "estilo") Long idEstilo
    ) {

        CancionCriteria criteria = new CancionCriteria.CancionCriteriaBuilder()
                .withIdEstilo(idEstilo)
                .build();

        List<CancionSimpleDto> canciones = this.service.getTop5Hits(criteria);

        return ResponseEntity.ok(canciones);
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseEntity<Page<CancionSimpleDto>> findAllFilters(
            @ModelAttribute CancionCriteria criteria,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<CancionSimpleDto> resultado = this.service.findAllFilters(criteria, pageable);

        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/sugerencias")
    public List<String> getSugerencias(CancionCriteria criteria, @RequestParam String term, @RequestParam String campo) {
        return service.getSugerencias(criteria, term, campo);
    }

    @GetMapping(value = "/fyp", produces = "application/json")
    public ResponseEntity<List<CancionSimpleDto>> getFYP(Authentication authentication) {
        try {
            String username = authentication.getName();
            UsuarioDto usuario = authService.getUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            List<CancionSimpleDto> cancionSimpleDtoList = this.service.getTop5FYP(usuario.getId());
            return ResponseEntity.ok(cancionSimpleDtoList);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/estilos", produces = "application/json")
    public ResponseEntity<List<String>> getEstilosUser(Authentication authentication) {
        try {
            String username = authentication.getName();
            UsuarioDto usuario = authService.getUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            List<String> estilosUsuario = this.service.getEstilosUsuario(usuario.getId());
            return ResponseEntity.ok(estilosUsuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
