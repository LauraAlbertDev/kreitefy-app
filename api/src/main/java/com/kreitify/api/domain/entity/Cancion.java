package com.kreitify.api.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    // Relaciones, lazy para cuando solo los necesite
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artista", nullable = false)
    private Artista artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estilo", nullable = false)
    private Estilo estilo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_album", nullable = false)
    private Album album;

    @Formula("""
    (select avg(v.valoracion)
     from valoracion v
     where v.id_cancion = id)
    """)
    private Double valoracion;

    @Column(nullable = false)
    private Long duracionSegundos;

    @Column(nullable = false)
    private Long reproducciones = 0L;

    @Column(nullable = false)
    private LocalDate fecha;

    @OneToMany(
            mappedBy = "cancion",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Valoracion> valoraciones = new ArrayList<>();

    @OneToMany(
            mappedBy = "cancion",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Reproduccion> reproduccionesArray = new ArrayList<>();

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Artista getArtista() {return artista;}

    public void setArtista(Artista artista) {this.artista = artista;}

    public Estilo getEstilo() {return estilo;}

    public void setEstilo(Estilo estilo) {this.estilo = estilo;}

    public Album getAlbum() {return album;}

    public void setAlbum(Album album) {this.album = album;}

    public Long getDuracionSegundos() {return duracionSegundos;}

    public void setDuracionSegundos(Long duracionSegundos) {this.duracionSegundos = duracionSegundos;}

    public Long getReproducciones() {return reproducciones;}

    public void setReproducciones(Long reproducciones) {this.reproducciones = reproducciones;}

    public Double getValoracion() {return valoracion;}

    public void setValoracion(Double valoracion) {this.valoracion = valoracion;}

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}