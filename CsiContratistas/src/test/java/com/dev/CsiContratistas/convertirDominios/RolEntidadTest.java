package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RolEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        // Arrange
        Rol domain = new Rol(
                1,
                "ADMIN",
                "Acceso total al sistema",
                LocalDateTime.of(2024, 7, 1, 10, 0),
                true
        );

        // Act
        RolEntidad entidad = RolEntidad.fromDomainModel(domain);

        // Assert
        assertNotNull(entidad);
        assertEquals(1, entidad.getId_rol());
        assertEquals("ADMIN", entidad.getNombre());
        assertEquals("Acceso total al sistema", entidad.getDescripcion());
        assertEquals(LocalDateTime.of(2024, 7, 1, 10, 0), entidad.getFecha_asignacion());
        assertTrue(entidad.getEstado());
        assertNull(entidad.getRolesUsuarios()); // por defecto le estás pasando null
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        // Arrange
        RolEntidad entidad = new RolEntidad(
                2,
                "USER",
                "Acceso limitado",
                LocalDateTime.of(2024, 6, 15, 8, 30),
                false,
                null
        );

        // Act
        Rol domain = entidad.toDomainModel();

        // Assert
        assertNotNull(domain);
        assertEquals(2, domain.getId_rol());
        assertEquals("USER", domain.getNombre());
        assertEquals("Acceso limitado", domain.getDescripcion());
        assertEquals(LocalDateTime.of(2024, 6, 15, 8, 30), domain.getFecha_asignacion());
        assertFalse(domain.getEstado());
    }
}
