package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.UsuarioDto;

import java.util.Optional;

public interface AuthService {
    public UsuarioDto register(UsuarioDto usuarioDto);
    Optional<UsuarioDto> getUser(String username);
}
