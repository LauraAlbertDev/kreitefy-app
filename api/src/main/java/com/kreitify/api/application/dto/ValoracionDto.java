package com.kreitify.api.application.dto;

public class ValoracionDto {
    private Long id;
    private Double valoracion;
    private Long cancionId;
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public Long getCancionId() {
        return cancionId;
    }

    public void setCancionId(Long cancionId) {
        this.cancionId = cancionId;
    }

    public Long getUsuarioId() { return usuarioId; }

    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
