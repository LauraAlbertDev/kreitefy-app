package com.kreitify.api.domain.entity;

import com.kreitify.api.domain.annotation.RestrictedBy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@RestrictedBy(dependentEntity = Cancion.class, fieldName = "artista", displayName = "canciones")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String nombre;

    public void setId(Long id) {this.id = id;}

    public Long getId() {return id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}
}