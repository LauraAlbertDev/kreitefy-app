package com.kreitify.api.infraestructure.rest.auth;

import com.kreitify.api.application.dto.ApiError;
import com.kreitify.api.application.dto.LoginDto;
import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.dto.UsuarioRegisterDto;
import com.kreitify.api.application.service.AuthService;
import com.kreitify.api.domain.entity.Rol;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(
            AuthService authService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(
            @RequestBody LoginDto loginDto,
            HttpServletResponse response
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            UsuarioDto user = authService
                    .getUser(loginDto.getUsername())
                    .orElseThrow();

            String token = jwtService.generateToken(user);

            ResponseCookie cookie = ResponseCookie.from("access_token", token)
                    .httpOnly(true)
                    .secure(false) // TRUE in production HTTPS
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Lax")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(user);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UsuarioRegisterDto usuarioDto,
            HttpServletResponse response
    ) {
        try {
            usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

            UsuarioDto dto = new UsuarioDto();
            dto.setNombre(usuarioDto.getNombre());
            dto.setApellidos(usuarioDto.getApellidos());
            dto.setUsername(usuarioDto.getUsername());
            dto.setEmail(usuarioDto.getEmail());
            dto.setPassword(usuarioDto.getPassword());
            dto.setRol(Rol.USUARIO);

            UsuarioDto registeredUser = authService.register(dto);

            String token = jwtService.generateToken(registeredUser);

            ResponseCookie cookie = ResponseCookie.from("access_token", token)
                    .httpOnly(true)
                    .secure(false) // Cambiar a TRUE en producción (HTTPS)
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Lax")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage().toLowerCase().contains("email")) {
                return ResponseEntity.status(409).body(new ApiError("email", "email already exists"));
            }
            if (e.getMessage().toLowerCase().contains("username")) {
                return ResponseEntity.status(409).body(new ApiError("username", "username already exists"));
            }
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(
            @RequestBody UsuarioRegisterDto usuarioDto,
            HttpServletResponse response
    ) {
        try {
            usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

            UsuarioDto dto = new UsuarioDto();
            dto.setNombre(usuarioDto.getNombre());
            dto.setApellidos(usuarioDto.getApellidos());
            dto.setUsername(usuarioDto.getUsername());
            dto.setEmail(usuarioDto.getEmail());
            dto.setPassword(usuarioDto.getPassword());
            dto.setRol(Rol.ADMIN); // <--- Forzamos explícitamente el rol de ADMIN

            UsuarioDto registeredUser = authService.register(dto);

            String token = jwtService.generateToken(registeredUser);

            ResponseCookie cookie = ResponseCookie.from("access_token", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Lax")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage().toLowerCase().contains("email")) {
                return ResponseEntity.status(409).body(new ApiError("email", "email already exists"));
            }
            if (e.getMessage().toLowerCase().contains("username")) {
                return ResponseEntity.status(409).body(new ApiError("username", "username already exists"));
            }
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Rol[]> getRoles() {
        return ResponseEntity.ok(Rol.values());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse response
    ) {

        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false) // true in production HTTPS
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh/me")
    public ResponseEntity<UsuarioDto> me(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).build();
            }

            String username = authentication.getName();

            UsuarioDto user = authService
                    .getUser(username)
                    .orElseThrow();

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}