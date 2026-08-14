package com.kreitify.api.application.service.impl;

import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.mapper.UsuarioMapper;
import com.kreitify.api.application.service.AuthService;
import com.kreitify.api.domain.entity.Usuario;
import com.kreitify.api.domain.exception.EntityInUseException;
import com.kreitify.api.domain.persistence.UsuarioPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UsuarioPersistence usuarioPersistence;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UsuarioPersistence usuarioPersistence, UsuarioMapper usuarioMapper, @Lazy PasswordEncoder passwordEncoder) {
        this.usuarioPersistence = usuarioPersistence;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioDto register(UsuarioDto usuarioDto) {
        if (usuarioPersistence.existsByUsernameIgnoreCase(usuarioDto.getUsername())) {
            throw new EntityInUseException("Username already exists");
        }
        if (usuarioPersistence.existsByEmailIgnoreCase(usuarioDto.getEmail())) {
            throw new EntityInUseException("Email already exists");
        }
        usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        return usuarioMapper.toDto(usuarioPersistence.save(usuario));
    }

    @Override
    public Optional<UsuarioDto> getUser(String username) {
        Optional<Usuario> usuario = usuarioPersistence.findByUsername(username);
        return usuario.map(usuarioMapper::toDto);
    }
}
