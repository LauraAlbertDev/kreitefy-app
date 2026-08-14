package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.dto.ReproduccionCreateDto;
import com.kreitify.api.application.dto.ReproduccionDto;
import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.service.AuthService;
import com.kreitify.api.application.service.CancionService;
import com.kreitify.api.application.service.ReproduccionService;
import com.kreitify.api.application.service.UsuarioService;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping({"/reproducciones", "/reproduccion"})
public class ReproduccionRestController extends BaseRestController<ReproduccionDto, Long, ReproduccionService> {
    private final UsuarioService usuarioService;
    private final CancionService cancionService;
    private final AuthService authService;

    protected ReproduccionRestController(ReproduccionService service, UsuarioService usuarioService, CancionService cancionService, AuthService authService) {
        super(service);
        this.usuarioService = usuarioService;
        this.cancionService = cancionService;
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ReproduccionDto> register(@RequestBody ReproduccionCreateDto dto, Authentication authentication) {
        try {
            LocalDateTime fecha = LocalDateTime.now();
            String username = authentication.getName();
            UsuarioDto usuario = authService.getUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            cancionService.registrarReproduccion(dto.getCancionId());

            ReproduccionDto reproduccionDto = new ReproduccionDto();
            reproduccionDto.setFecha(fecha);
            reproduccionDto.setUsuarioId(usuario.getId());
            reproduccionDto.setCancionId(dto.getCancionId());
            return super.create(reproduccionDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/tus-reproducciones")
    public ResponseEntity<List<ReproduccionDto>> obtenerTusReproducciones() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuarioDto = usuarioService.findByUsername(username);
        Long usuarioId = usuarioDto.getId();
        List<ReproduccionDto> historial = this.service.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(historial);
    }
}
