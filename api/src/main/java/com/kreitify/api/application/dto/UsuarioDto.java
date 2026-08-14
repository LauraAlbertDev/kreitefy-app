package com.kreitify.api.application.dto;

import com.kreitify.api.domain.entity.Rol;
import jakarta.validation.constraints.*;

public class UsuarioDto {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;
    @NotBlank
    @Size(min = 10, max = 100)
    private String password;
    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;
    @NotBlank
    @Size(min = 10, max = 100)
    private String apellidos;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Formato de email inválido")
    private String email;
    @NotNull
    private Rol rol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
