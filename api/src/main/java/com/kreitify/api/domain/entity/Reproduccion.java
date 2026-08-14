package com.kreitify.api.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reproduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cancion", nullable = false)
    private Cancion cancion;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public LocalDateTime getFecha() { return fecha; }

    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public Cancion getCancion() {return cancion;}

    public void setCancion(Cancion cancion) {this.cancion = cancion;}
}
