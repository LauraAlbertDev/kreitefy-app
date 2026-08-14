package com.kreitify.api.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CancionCreateDto {
    private Long id;
    @NotBlank
    @Size(min=3, max = 100)
    private String titulo;
    @NotBlank
    private String artista;
    @NotBlank
    private String estilo;
    @Size(max = 100)
    private String album;
    private String imagenAlbum;
    @NotNull
    @Positive
    private Long duracionSegundos;
    private Double valoracion;
    private Long reproducciones;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @NotNull
    private Long artistaId;
    @NotNull
    private Long estiloId;
    @NotNull
    private Long albumId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImagenAlbum() {
        return imagenAlbum;
    }

    public void setImagenAlbum(String imagenAlbum) {
        this.imagenAlbum = imagenAlbum;
    }

    public Long getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(Long duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public Long getReproducciones() {
        return reproducciones;
    }

    public void setReproducciones(Long reproducciones) {
        this.reproducciones = reproducciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getArtistaId() {
        return artistaId;
    }

    public void setArtistaId(Long artistaId) {
        this.artistaId = artistaId;
    }

    public Long getEstiloId() {
        return estiloId;
    }

    public void setEstiloId(Long estiloId) {
        this.estiloId = estiloId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
