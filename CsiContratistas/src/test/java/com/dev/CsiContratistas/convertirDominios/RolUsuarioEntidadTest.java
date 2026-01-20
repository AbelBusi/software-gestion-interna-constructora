package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.UsuarioEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolUsuarioEntidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RolUsuarioEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        Rol rol = new Rol(
                1,
                "ADMIN",
                "Rol administrador del sistema",
                LocalDateTime.of(2024, 1, 1, 12, 0),
                true
        );

        Usuario usuario = new Usuario(
                2,
                null,
                "admin@correo.com",
                "clave123",
                LocalDateTime.of(2024, 1, 1, 13, 0),
                true
        );

        Rol_usuario rolUsuario = new Rol_usuario(
                10,
                rol,
                usuario,
                LocalDateTime.of(2024, 5, 1, 12, 0),
                true
        );

        RolUsuarioEntidad entidad = RolUsuarioEntidad.fromDomainModel(rolUsuario);

        assertNotNull(entidad);
        assertEquals(10, entidad.getId_rolUsuario());
        assertEquals("ADMIN", entidad.getId_rol().getNombre());
        assertEquals("admin@correo.com", entidad.getId_usuario().getCorreo());
        assertEquals(LocalDateTime.of(2024, 5, 1, 12, 0), entidad.getFecha_asignacion());
        assertTrue(entidad.getEstado());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        RolEntidad rolEntidad = new RolEntidad(
                1,
                "USER",
                "Rol básico",
                LocalDateTime.of(2024, 1, 2, 10, 0),
                true,
                null
        );

        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setId_usuario(3);
        usuarioEntidad.setCorreo("user@correo.com");
        usuarioEntidad.setClave_acceso("clave456");
        usuarioEntidad.setFecha_asignacion(LocalDateTime.of(2024, 1, 2, 11, 0));
        usuarioEntidad.setEstado(true);

        RolUsuarioEntidad entidad = new RolUsuarioEntidad(
                20,
                rolEntidad,
                usuarioEntidad,
                LocalDateTime.of(2024, 6, 15, 9, 0),
                false
        );

        Rol_usuario rolUsuario = entidad.toDomainModel();

        assertNotNull(rolUsuario);
        assertEquals(20, rolUsuario.getId_rol_usuario());
        assertEquals("USER", rolUsuario.getId_rol().getNombre());
        assertEquals("Rol básico", rolUsuario.getId_rol().getDescripcion());
        assertEquals("user@correo.com", rolUsuario.getId_usuario().getCorreo());
        assertEquals(LocalDateTime.of(2024, 6, 15, 9, 0), rolUsuario.getFecha_asignacion());
        assertFalse(rolUsuario.getEstado());
    }

}
