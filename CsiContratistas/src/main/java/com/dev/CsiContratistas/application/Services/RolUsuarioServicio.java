package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RolUsuarioServicio implements ICrearCasoUso<Rol_usuario>, ILeerCasoUso<Rol_usuario>, IModificarCasoUso<Rol_usuario>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Rol_usuario> leerRolUsuario;


    private final ICrearCasoUso<Rol_usuario> crearRolUsuario;

    private final IModificarCasoUso<Rol_usuario> modificarRolUsuario;

    private final IEliminarCasoUso<Integer> eliminarRolPorId;

    @Override
    public Rol_usuario crearObjeto(Rol_usuario rolUsuario) {
        if (rolUsuario.getFecha_asignacion() == null) {
        rolUsuario.setFecha_asignacion(LocalDateTime.now());
    }
    if (rolUsuario.getEstado() == null) {
        rolUsuario.setEstado(true);
    }
    return crearRolUsuario.crearObjeto(rolUsuario);
    }

    @Override
    public Integer eliminarObjeto(Integer idRolUsuario) {
        return eliminarRolPorId.eliminarObjeto(idRolUsuario);
    }

    @Override
    public Optional<Rol_usuario> leerObjeto(Integer id) {
        return leerRolUsuario.leerObjeto(id);
    }

    @Override
    public List<Rol_usuario> leerObjetos() {
        return leerRolUsuario.leerObjetos();
    }

    @Override
    public Optional<Rol_usuario> modificarObjeto(Integer id,Rol_usuario rolUsuario) {
        return modificarRolUsuario.modificarObjeto(id,rolUsuario);
    }
    public Optional<Rol_usuario> obtenerPorUsuarioId(Integer idUsuario) {
    return leerObjetos()
            .stream()
            .filter(ru -> ru.getId_usuario() != null && ru.getId_usuario().getId_usuario().equals(idUsuario))
            .findFirst();
    }
    public Integer eliminarPorUsuario(Integer idUsuario) {
        List<Rol_usuario> rolesUsuario = leerRolUsuario.leerObjetos().stream()
                .filter(ru -> ru.getId_usuario() != null && 
                             ru.getId_usuario().getId_usuario().equals(idUsuario))
                .toList();
        
        rolesUsuario.forEach(ru -> eliminarRolPorId.eliminarObjeto(ru.getId_rol_usuario()));
        
        return rolesUsuario.size();
    }

    public Rol_usuario asignarRolAUsuario(Integer idUsuario, Integer idRol, Usuario usuario) {
        eliminarPorUsuario(idUsuario);
    
        Rol_usuario nuevoRol = new Rol_usuario();
        nuevoRol.setId_usuario(usuario);
    
         Rol rol = new Rol();
            rol.setId_rol(idRol);
          nuevoRol.setId_rol(rol);
    
        return crearObjeto(nuevoRol);
    }
    public String obtenerRolPorUsuario(Integer idUsuario) {
        return leerObjetos().stream()
                .filter(ru -> ru.getId_usuario() != null
                        && ru.getId_usuario().getId_usuario().equals(idUsuario)
                        && Boolean.TRUE.equals(ru.getEstado()))
                .sorted((a, b) -> b.getFecha_asignacion().compareTo(a.getFecha_asignacion())) // más reciente primero
                .map(ru -> ru.getId_rol().getNombre())
                .findFirst()
                .orElse("Sin rol");
    }
    public int contarUsuariosPorRol(Integer idRol) {
    return (int) leerObjetos().stream()
            .filter(ru -> ru.getId_rol() != null && ru.getId_rol().getId_rol().equals(idRol))
            .count();
}
}