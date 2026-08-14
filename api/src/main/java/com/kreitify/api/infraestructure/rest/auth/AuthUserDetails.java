package com.kreitify.api.infraestructure.rest.auth;

import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.domain.entity.Rol;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Rol role;

    public AuthUserDetails(UsuarioDto usuarioDto) {
        this.username = usuarioDto.getUsername();
        this.password = usuarioDto.getPassword();
        this.role = usuarioDto.getRol();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + this.role.name())
        );
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
