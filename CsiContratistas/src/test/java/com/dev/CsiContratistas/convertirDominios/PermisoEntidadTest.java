package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.infrastructure.Entity.PermisoEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PermisoEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2024, 6, 1, 10, 30);
        Permiso permiso = new Permiso(1, "GESTION_USUARIOS", "Permite gestionar usuarios", fecha, true);

        // Act
        PermisoEntidad entidad = PermisoEntidad.fromDomainModel(permiso);

        // Assert
        assertNotNull(entidad, "La entidad no debe ser nula");
        assertEquals(1, entidad.getId_permiso());
        assertEquals("GESTION_USUARIOS", entidad.getNombre());
        assertEquals("Permite gestionar usuarios", entidad.getDescripcion());
        assertEquals(fecha, entidad.getFecha_asignacion());
        assertTrue(entidad.getEstado());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2025, 1, 15, 15, 0);
        PermisoEntidad entidad = new PermisoEntidad(2, "GESTION_ROLES", "Permite gestionar roles", fecha, false);

        // Act
        Permiso permiso = entidad.toDomainModel();

        // Assert
        assertNotNull(permiso, "El dominio no debe ser nulo");
        assertEquals(2, permiso.getId_permiso());
        assertEquals("GESTION_ROLES", permiso.getNombre());
        assertEquals("Permite gestionar roles", permiso.getDescripcion());
        assertEquals(fecha, permiso.getFecha_asignacion());
        assertFalse(permiso.getEstado());
    }


}
