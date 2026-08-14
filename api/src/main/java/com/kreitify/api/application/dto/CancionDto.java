package com.kreitify.api.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CancionDto {

    private Long id;
    @NotBlank
    @Size(min=3, max = 100)
    private String titulo;
    @NotBlank
    private String artista;
    @NotBlank
    private String estilo;
    @NotBlank
    private String album;
    private String imagenAlbum;
    @NotNull
    @Positive
    private Long duracionSegundos;
    private Double valoracion;
    private Long reproducciones;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getArtista() {return artista;}

    public void setArtista(String artista) {this.artista = artista;}

    public String getEstilo() {return estilo;}

    public void setEstilo(String estilo) {this.estilo = estilo;}

    public String getAlbum() {return album;}

    public void setAlbum(String album) {this.album = album;}

    public Long getDuracionSegundos() {return duracionSegundos;}

    public void setDuracionSegundos(Long duracionSegundos) {this.duracionSegundos = duracionSegundos;}

    public String getImagenAlbum() {return imagenAlbum;}

    public void setImagenAlbum(String imagenAlbum) {this.imagenAlbum = imagenAlbum;}

    public Double getValoracion() {return valoracion;}

    public void setValoracion(Double valoracion) {this.valoracion = valoracion;}

    public Long getReproducciones() {return reproducciones;}

    public void setReproducciones(Long reproducciones) {this.reproducciones = reproducciones;}

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
