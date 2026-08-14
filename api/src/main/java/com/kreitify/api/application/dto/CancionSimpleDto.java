package com.kreitify.api.application.dto;

public class CancionSimpleDto {
    private Long id;
    private String titulo;
    private String artista;
    private String imagenAlbum;

    public CancionSimpleDto(Long id, String titulo, String artista, String imagenAlbum) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.imagenAlbum = imagenAlbum;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getImagenAlbum() {
        return imagenAlbum;
    }
}
