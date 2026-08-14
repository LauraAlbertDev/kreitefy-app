package com.kreitify.api.infraestructure.rest.controllers;

import com.kreitify.api.application.dto.LoginResponse;
import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.service.AuthService;
import com.kreitify.api.application.service.UsuarioService;
import com.kreitify.api.infraestructure.rest.BaseRestController;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/usuarios", "/usuario"})
public class UsuarioRestController extends BaseRestController<UsuarioDto, Long, UsuarioService> {
    private final AuthService authService;
    public UsuarioRestController(UsuarioService service,  AuthService authService) {
        super(service);
        this.authService = authService;
    }

    @Transactional
    @PutMapping(
            value = "/profile/me",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<LoginResponse> updatePerfil(@RequestBody UsuarioDto dto, Authentication authentication) {
        try {
            String username = authentication.getName();
            UsuarioDto usuario = authService.getUser(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            LoginResponse response = service.updateProfile(usuario.getId(), dto);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile/me")
    public ResponseEntity<UsuarioDto> getPerfil(Authentication authentication) {
        try {
            String username = authentication.getName();
            UsuarioDto usuario = authService.getUser(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UsuarioDto>> getAll(Pageable pageable) {
        return super.getAll(pageable);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDto> findById(Long id) {
        return super.findById(id);
    }
}