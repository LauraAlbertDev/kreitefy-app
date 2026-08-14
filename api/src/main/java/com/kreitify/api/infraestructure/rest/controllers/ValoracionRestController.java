package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.dto.ValoracionDto;
import com.kreitify.api.application.service.AuthService;
import com.kreitify.api.application.service.ValoracionService;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping({"/valoraciones", "/valoracion"})
public class ValoracionRestController extends BaseRestController<ValoracionDto, Long, ValoracionService> {
    private final AuthService authService;

    protected ValoracionRestController(ValoracionService service, AuthService authService) {
        super(service);
        this.authService = authService;
    }

    @GetMapping(value = "/cancion/{cancionId}")
    public ResponseEntity<ValoracionDto> findValoracionDtoByCancionIdAndUsuarioId(@PathVariable Long cancionId, Authentication authentication) {
        try {
            String username = authentication.getName();
            UsuarioDto usuario = authService.getUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            Optional<ValoracionDto> valoracionDto = this.service.findValoracionDtoByCancionIdAndUsuarioId(cancionId, usuario.getId());
            return ResponseEntity.ok(valoracionDto.orElse(null));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO')")
    public ResponseEntity<ValoracionDto> create(ValoracionDto dto) {
        return super.create(dto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO')")
    public ResponseEntity<ValoracionDto> update(Long id, ValoracionDto dto) {
        return super.update(id, dto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('USUARIO')")
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }
}
