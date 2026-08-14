package com.kreitify.api.application.service;

import com.kreitify.api.application.dto.LoginResponse;
import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.application.mapper.EntityMapper;
import com.kreitify.api.domain.entity.Usuario;
import com.kreitify.api.domain.persistence.BasePersistence;
import com.kreitify.api.domain.persistence.UsuarioPersistence;
import com.kreitify.api.infraestructure.rest.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService extends BaseService<Usuario, UsuarioDto, Long>{
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioPersistence usuarioPersistence;
    protected UsuarioService(BasePersistence<Usuario, Long> persistence, EntityMapper<UsuarioDto, Usuario> mapper, JwtService jwtService, UsuarioPersistence usuarioPersistence) {
        super(persistence, mapper);
        this.jwtService = jwtService;
        this.usuarioPersistence = usuarioPersistence;
    }

    @Transactional
    public LoginResponse updateProfile(Long id, UsuarioDto dto) {
        Usuario usuario = findByIdOrThrow(id);
        String passwordOriginalValida = usuario.getPassword();
        String passwordSuministrada = dto.getPassword();
        dto.setPassword(null);

        try {
            mapper.updateEntityFromDto(dto, usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear los datos", e);
        }

        if (passwordSuministrada != null && !passwordSuministrada.trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(passwordSuministrada));
        } else usuario.setPassword(passwordOriginalValida);
        Usuario usuarioGuardado = persistence.save(usuario);
        UsuarioDto usuarioDtoActualizado = mapper.toDto(usuarioGuardado);
        String nuevoToken = jwtService.generateToken(usuarioDtoActualizado);

        return new LoginResponse(nuevoToken);
    }

    @Transactional(readOnly = true)
    public UsuarioDto findByUsername(String username) {
        Usuario usuario = usuarioPersistence.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el username: " + username));
        return mapper.toDto(usuario);
    }
}
