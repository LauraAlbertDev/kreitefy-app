package com.kreitify.api.application.mapper;

import com.kreitify.api.application.dto.UsuarioDto;
import com.kreitify.api.domain.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDto, Usuario> {
    default Usuario fromId(String username) {
        if (username == null) {
            return null;
        }
        Usuario user = new Usuario();
        user.setUsername(username);
        return user;
    }

    default Usuario fromId(Long id) {
        if (id == null) return null;

        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }
}