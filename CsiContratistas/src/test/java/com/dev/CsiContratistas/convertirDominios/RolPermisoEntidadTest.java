package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.infrastructure.Entity.PermisoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolPermisoEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RolPermisoEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        Rol rol = new Rol(1, "ADMIN", "Rol admin", LocalDateTime.of(2024, 1, 1, 12, 0), true);
        Permiso permiso = new Permiso(2, "GESTIONAR_USUARIOS", "Acceso a gestión de usuarios", LocalDateTime.of(2024, 6, 1, 10, 0), true);

        Rol_Permiso domain = new Rol_Permiso(
                100,
                rol,
                permiso,
                LocalDateTime.of(2024, 7, 1, 10, 30),
                true
        );

        RolPermisoEntidad entidad = RolPermisoEntidad.fromDomainModel(domain);

        assertNotNull(entidad);
        assertEquals(100, entidad.getId_rol_permiso());
        assertEquals("ADMIN", entidad.getId_rol().getNombre());
        assertEquals("GESTIONAR_USUARIOS", entidad.getId_permiso().getNombre());
        assertEquals(LocalDateTime.of(2024, 7, 1, 10, 30), entidad.getFecha_asignacion());
        assertTrue(entidad.getEstado());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        RolEntidad rolEntidad = new RolEntidad(1, "USER", "Rol básico", LocalDateTime.of(2024, 6, 1, 10, 0), true, null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(2, "VER_REPORTES", "Permite ver reportes", LocalDateTime.of(2024, 6, 1, 10, 0), true);

        RolPermisoEntidad entidad = new RolPermisoEntidad(
                200,
                rolEntidad,
                permisoEntidad,
                LocalDateTime.of(2024, 7, 5, 8, 0),
                false
        );

        Rol_Permiso domain = entidad.toDomainModel();

        assertNotNull(domain);
        assertEquals(200, domain.getId_rol_Permiso());
        assertEquals("USER", domain.getId_rol().getNombre());
        assertEquals("VER_REPORTES", domain.getId_permiso().getNombre());
        assertEquals(LocalDateTime.of(2024, 7, 5, 8, 0), domain.getFecha_asignacion());
        assertFalse(domain.getEstado());
    }
}
