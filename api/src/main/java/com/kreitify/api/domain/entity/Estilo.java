package com.kreitify.api.domain.entity;

import com.kreitify.api.domain.annotation.RestrictedBy;
import jakarta.persistence.*;

@Entity
@RestrictedBy(dependentEntity = Cancion.class, fieldName = "estilo", displayName = "canciones")
public class Estilo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}
}