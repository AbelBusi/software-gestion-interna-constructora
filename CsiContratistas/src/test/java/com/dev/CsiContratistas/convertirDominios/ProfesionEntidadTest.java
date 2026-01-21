package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.infrastructure.Entity.ProfesionEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProfesionEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        System.out.println("Iniciando test: convertir Profesiones (dominio → entidad)");

        // Arrange
        Profesiones domain = new Profesiones(
                7,
                "Ingeniería de Datos",
                "Diseño y gestión de pipelines de datos",
                LocalDateTime.of(2023, 8, 10, 9, 30),
                true
        );

        // Act
        ProfesionEntidad entidad = ProfesionEntidad.fromDomainModel(domain);

        // Assert
        assertNotNull(entidad);
        assertEquals(7, entidad.getId_profesion());
        assertEquals("Ingeniería de Datos", entidad.getNombre());
        assertEquals("Diseño y gestión de pipelines de datos", entidad.getDescripcion());
        assertEquals(LocalDateTime.of(2023, 8, 10, 9, 30), entidad.getFecha_asignacion());
        assertTrue(entidad.getEstado());
        assertNotNull(entidad.getRamas());
        assertNotNull(entidad.getCargos());
        assertTrue(entidad.getRamas().isEmpty());
        assertTrue(entidad.getCargos().isEmpty());

        System.out.println("Conversión dominio → entidad exitosa\n");
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        System.out.println("Iniciando test: convertir ProfesionEntidad (entidad → dominio)");

        // Arrange
        ProfesionEntidad entidad = new ProfesionEntidad(
                7,
                "Ingeniería de Datos",
                "Diseño y gestión de pipelines de datos",
                LocalDateTime.of(2023, 8, 10, 9, 30),
                true
        );

        // Act
        Profesiones domain = entidad.toDomainModel();

        // Assert
        assertNotNull(domain);
        assertEquals(7, domain.getId_profesion());
        assertEquals("Ingeniería de Datos", domain.getNombre());
        assertEquals("Diseño y gestión de pipelines de datos", domain.getDescripcion());
        assertEquals(LocalDateTime.of(2023, 8, 10, 9, 30), domain.getFecha_asignacion());
        assertTrue(domain.getEstado());

        System.out.println("Conversión entidad → dominio exitosa\n");
    }

    @Test
    void debeCrearEntidadSoloConId() {
        // Arrange
        int id = 99;

        // Act
        ProfesionEntidad entidad = ProfesionEntidad.soloConId(id);

        // Assert
        assertNotNull(entidad);
        assertEquals(id, entidad.getId_profesion());
        assertNull(entidad.getNombre(), "Nombre debe quedar null cuando solo se crea con ID");
        assertNull(entidad.getDescripcion(), "Descripción debe quedar null cuando solo se crea con ID");
    }

    @Test
    void prePersistDebeAsignarFechaSiEsNull() {
        // Arrange
        ProfesionEntidad entidad = new ProfesionEntidad(
                0, "Tester", "Sin descripción", null, true
        );

        // Act
        entidad.asignarFechaAsignacion(); // Simulamos @PrePersist

        // Assert
        assertNotNull(entidad.getFecha_asignacion(), "La fecha_asignacion no debería ser null después de @PrePersist");
    }
}
