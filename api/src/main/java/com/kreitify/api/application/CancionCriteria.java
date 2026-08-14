package com.kreitify.api.application;

public class CancionCriteria {
    private Long idEstilo;
    private String titulo;
    private String artista;
    private String album;

    public CancionCriteria() {
    }

    public Long getIdEstilo() { return idEstilo; }
    public String getTitulo() {return titulo;}
    public String getArtista() {return artista;}
    public String getAlbum() {return album;}

    public void setIdEstilo(Long idEstilo) {this.idEstilo = idEstilo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public void setArtista(String artista) {this.artista = artista;}
    public void setAlbum(String album) {this.album = album;}

    public CancionCriteria(CancionCriteriaBuilder builder) {
        this.idEstilo = builder.idEstilo;
        this.titulo = builder.titulo;
        this.artista = builder.artista;
        this.album = builder.album;
    }

    public static class CancionCriteriaBuilder {
        private Long idEstilo;
        private String titulo;
        private String artista;
        private String album;

        public CancionCriteriaBuilder withIdEstilo(Long idEstilo) {
            this.idEstilo = idEstilo;
            return this;
        }

        public CancionCriteria build() {
            return new CancionCriteria(this);
        }
    }
}
