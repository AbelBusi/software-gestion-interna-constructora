package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaRolRepositorioAdapter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RolServicio implements ICrearCasoUso<Rol>, ILeerCasoUso<Rol>, IModificarCasoUso<Rol>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Rol> leerRol;


    private final ICrearCasoUso<Rol> crearRol;

    private final IModificarCasoUso<Rol> modificarRol;

    private final IEliminarCasoUso<Integer> eliminarRolPorId;

    private final JpaRolRepositorioAdapter jpaRolRepositorioAdapter;

    @Override
    public Rol crearObjeto(Rol rol) {
        Optional <Rol> existrol =jpaRolRepositorioAdapter.buscarpornombre(rol.getNombre());
        if (existrol.isPresent()) {
            throw  new RuntimeException("El rol "+rol.getNombre()+" ya existe") ;
        }
        return crearRol.crearObjeto(rol);
    }
    public boolean existpornombre(String nombre, Integer idRol) {
        Optional<Rol> rolExistente = jpaRolRepositorioAdapter.buscarpornombre(nombre);
        return rolExistente.isPresent() && !rolExistente.get().getId_rol().equals(idRol);
    }
    @Override
    public Integer eliminarObjeto(Integer idRol) {
        return eliminarRolPorId.eliminarObjeto(idRol);
    }

    @Override
    public Optional<Rol> leerObjeto(Integer id) {
        return leerRol.leerObjeto(id);
    }

    @Override
    public List<Rol> leerObjetos() {
        return leerRol.leerObjetos();
    }

    @Override
    public Optional<Rol> modificarObjeto(Integer id,Rol rol) {
        return modificarRol.modificarObjeto(id,rol);
    }
}