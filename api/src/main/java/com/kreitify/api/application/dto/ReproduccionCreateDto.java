package com.kreitify.api.application.dto;

public class ReproduccionCreateDto {
    private Long id;
    private Long cancionId;
    private String cancionTitulo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCancionId() {
        return cancionId;
    }

    public void setCancionId(Long cancionId) {
        this.cancionId = cancionId;
    }

    public String getCancionTitulo() {
        return cancionTitulo;
    }

    public void setCancionTitulo(String cancionTitulo) {
        this.cancionTitulo = cancionTitulo;
    }
}
