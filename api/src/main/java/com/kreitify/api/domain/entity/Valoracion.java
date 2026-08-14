package com.kreitify.api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id_cancion", "id_usuario"}
        )
)
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cancion", nullable = false)
    private Cancion cancion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double valoracion;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Cancion getCancion() { return cancion; }

    public void setCancion(Cancion cancion) { this.cancion = cancion; }

    public Double getValoracion() { return valoracion; }

    public void setValoracion(Double valoracion) { this.valoracion = valoracion; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
