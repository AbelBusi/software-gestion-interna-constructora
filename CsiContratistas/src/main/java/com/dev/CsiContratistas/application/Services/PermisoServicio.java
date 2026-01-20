package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PermisoServicio implements ICrearCasoUso<Permiso>, ILeerCasoUso<Permiso>, IModificarCasoUso<Permiso>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Permiso> leerPermiso;


    private final ICrearCasoUso<Permiso> crearPermiso;

    private final IModificarCasoUso<Permiso> modificarPermiso;

    private final IEliminarCasoUso<Integer> eliminarPermisoPorId;

    @Override
    public Permiso crearObjeto(Permiso permiso) {
        return crearPermiso.crearObjeto(permiso);
    }

    @Override
    public Integer eliminarObjeto(Integer idPermiso) {
        return eliminarPermisoPorId.eliminarObjeto(idPermiso);
    }

    @Override
    public Optional<Permiso> leerObjeto(Integer id) {
        return leerPermiso.leerObjeto(id);
    }

    @Override
    public List<Permiso> leerObjetos() {
        return leerPermiso.leerObjetos();
    }

    @Override
    public Optional<Permiso> modificarObjeto(Integer id,Permiso permiso) {
        return modificarPermiso.modificarObjeto(id,permiso);
    }
}