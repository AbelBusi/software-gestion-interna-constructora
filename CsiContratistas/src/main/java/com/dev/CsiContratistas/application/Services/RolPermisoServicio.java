package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RolPermisoServicio implements ICrearCasoUso<Rol_Permiso>, ILeerCasoUso<Rol_Permiso>, IModificarCasoUso<Rol_Permiso>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Rol_Permiso> leerRolPermiso;

    private final ICrearCasoUso<Rol_Permiso> crearRolPermiso;

    private final IModificarCasoUso<Rol_Permiso> modificarRolPermiso;

    private final IEliminarCasoUso<Integer> eliminarRolPermisoPorId;

    @Override
    public Rol_Permiso crearObjeto(Rol_Permiso rolUsuario) {
        return crearRolPermiso.crearObjeto(rolUsuario);
    }

    @Override
    public Integer eliminarObjeto(Integer idRolUsuario) {
        return eliminarRolPermisoPorId.eliminarObjeto(idRolUsuario);
    }

    @Override
    public Optional<Rol_Permiso> leerObjeto(Integer id) {
        return leerRolPermiso.leerObjeto(id);
    }

    @Override
    public List<Rol_Permiso> leerObjetos() {
        return leerRolPermiso.leerObjetos();
    }

    @Override
    public Optional<Rol_Permiso> modificarObjeto(Integer id,Rol_Permiso rolUsuario) {
        return modificarRolPermiso.modificarObjeto(id,rolUsuario);
    }
    public List<Integer> obtenerIdsPermisosPorRol(Integer idRol) {
    return leerRolPermiso.leerObjetos().stream()
            .filter(rp -> rp.getId_rol().getId_rol().equals(idRol))
            .map(rp -> rp.getId_permiso().getId_permiso())
            .toList();
    }

    public void actualizarPermisosRol(Integer idRol, List<Integer> nuevosPermisos) {
    // Elimina todos los permisos actuales del rol
    List<Rol_Permiso> actuales = leerRolPermiso.leerObjetos().stream()
            .filter(rp -> rp.getId_rol().getId_rol().equals(idRol))
            .toList();
    for (Rol_Permiso rp : actuales) {
        eliminarRolPermisoPorId.eliminarObjeto(rp.getId_rol_Permiso());
    }
    // Agrega los nuevos permisos
    for (Integer idPermiso : nuevosPermisos) {
        Rol_Permiso nuevo = new Rol_Permiso();
        Rol rol = new Rol();
        rol.setId_rol(idRol);
        Permiso permiso = new Permiso();
        permiso.setId_permiso(idPermiso);
        nuevo.setId_rol(rol);
        nuevo.setId_permiso(permiso);
        nuevo.setFecha_asignacion(java.time.LocalDateTime.now());
        nuevo.setEstado(true);
        crearRolPermiso.crearObjeto(nuevo);
    }
    }


}