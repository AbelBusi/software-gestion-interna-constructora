package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.Entity.ProfesionEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RamaEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RamaEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        // Arrange
        Profesiones profesion = new Profesiones(1, "Ingeniería", "Área técnica", LocalDateTime.of(2024, 1, 1, 10, 0), true);
        Rama rama = new Rama(10, profesion, "Civil", "Rama de obras civiles", LocalDateTime.of(2024, 2, 2, 12, 0), true);

        // Act
        RamaEntidad entidad = RamaEntidad.fromDomainModel(rama);

        // Assert
        assertNotNull(entidad);
        assertEquals(10, entidad.getId_rama());
        assertEquals("Civil", entidad.getNombre());
        assertEquals("Rama de obras civiles", entidad.getDescripcion());
        assertEquals(LocalDateTime.of(2024, 2, 2, 12, 0), entidad.getFecha_asignacion());
        assertTrue(entidad.getEstado());
        assertNotNull(entidad.getId_profesion());
        assertEquals("Ingeniería", entidad.getId_profesion().getNombre());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        // Arrange
        ProfesionEntidad profesionEntidad = new ProfesionEntidad(2, "Arquitectura", "Diseño", LocalDateTime.of(2023, 5, 10, 9, 0), true);
        RamaEntidad entidad = new RamaEntidad(20, profesionEntidad, "Estructural", "Diseño estructural", LocalDateTime.of(2024, 3, 3, 14, 0), false, null);

        // Act
        Rama domain = entidad.toDomainModel();

        // Assert
        assertNotNull(domain);
        assertEquals(20, domain.getId_rama());
        assertEquals("Estructural", domain.getNombre());
        assertEquals("Diseño estructural", domain.getDescripcion());
        assertEquals(LocalDateTime.of(2024, 3, 3, 14, 0), domain.getFecha_asignacion());
        assertFalse(domain.getEstado());
        assertNotNull(domain.getId_profesion());
        assertEquals("Arquitectura", domain.getId_profesion().getNombre());
    }
}
