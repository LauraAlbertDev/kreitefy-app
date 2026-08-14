package com.kreitify.api.domain.entity;

import com.kreitify.api.domain.annotation.RestrictedBy;
import jakarta.persistence.*;

@Entity
@RestrictedBy(dependentEntity = Cancion.class, fieldName = "album", displayName = "canciones")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imagen;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}
}

