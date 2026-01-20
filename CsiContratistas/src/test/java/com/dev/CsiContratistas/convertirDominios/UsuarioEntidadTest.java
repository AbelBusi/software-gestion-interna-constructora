package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.infrastructure.Entity.EmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolUsuarioEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.UsuarioEntidad;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        Empleado empleado = new Empleado(1, null, "Juan", null, null, null, null, null, null, null);
        Usuario usuario = new Usuario(5, empleado, "juan@email.com", "1234", LocalDateTime.now(), true);

        UsuarioEntidad entidad = UsuarioEntidad.fromDomainModel(usuario);

        assertNotNull(entidad);
        assertEquals(usuario.getId_usuario(), entidad.getId_usuario());
        assertEquals("juan@email.com", entidad.getCorreo());
        assertEquals("1234", entidad.getClave_acceso());
        assertNotNull(entidad.getId_empleado());
        assertEquals(1, entidad.getId_empleado().getId_empleado());
    }

    /*
    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        EmpleadoEntidad empleadoEntidad = new EmpleadoEntidad();
        empleadoEntidad.setId_empleado(1);
        UsuarioEntidad entidad = new UsuarioEntidad(5, empleadoEntidad, "correo@prueba.com", "clave", LocalDateTime.now(), true, List.of());

        Usuario usuario = entidad.toDomainModel();

        assertNotNull(usuario);
        assertEquals(5, usuario.getId_usuario());
        assertEquals("correo@prueba.com", usuario.getCorreo());
        assertNotNull(usuario.getId_empleado());
        assertEquals(1, usuario.getId_empleado().getId_empleado());
    }*/

    @Test
    void debeRetornarRolesComoAuthorities() {
        RolEntidad rol = new RolEntidad();
        rol.setNombre("admin");

        RolUsuarioEntidad rolUsuario = new RolUsuarioEntidad();
        rolUsuario.setId_rol(rol);

        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setUsuarios(List.of(rolUsuario));

        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) usuario.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority("ROLE_ADMIN"), authorities.get(0));
    }

    @Test
    void debeRetornarValoresCorrectosDeUserDetails() {
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setCorreo("usuario@email.com");
        usuario.setClave_acceso("secreta");
        usuario.setEstado(true);

        assertEquals("usuario@email.com", usuario.getUsername());
        assertEquals("secreta", usuario.getPassword());
        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
        assertTrue(usuario.isEnabled());
    }
}
