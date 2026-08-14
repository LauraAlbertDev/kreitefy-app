package com.kreitify.api.application.service;

import com.kreitify.api.domain.entity.Cancion;
import com.kreitify.api.domain.persistence.CancionPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancionServiceRegistrarReproduccionTest {

    @Mock
    private CancionPersistence persistence;

    @InjectMocks
    private CancionService service;

    private Cancion cancion;

    @BeforeEach
    void setUp() {
        cancion = new Cancion();
        cancion.setId(1L);
        cancion.setReproducciones(10L);
    }

    @Test
    void shouldIncrementReproduccionesWhenValidCancionProvided() {
        when(persistence.findById(1L)).thenReturn(Optional.of(cancion));

        service.registrarReproduccion(1L);

        assertEquals(11L, cancion.getReproducciones());
        verify(persistence).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenInvalidCancionProvided() {
        Long invalidId = 99L;
        when(persistence.findById(invalidId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.registrarReproduccion(invalidId)
        );

        assertEquals(
                "Canción no encontrada con id: 99",
                exception.getMessage()
        );

        verify(persistence).findById(invalidId);
    }
}