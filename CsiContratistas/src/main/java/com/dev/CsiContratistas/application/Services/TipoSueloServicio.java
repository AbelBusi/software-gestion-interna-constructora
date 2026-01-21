package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TipoSueloServicio implements ICrearCasoUso<Tipo_suelo>, ILeerCasoUso<Tipo_suelo>, IModificarCasoUso<Tipo_suelo>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Tipo_suelo> leerTipoSuelo;

    private final ICrearCasoUso<Tipo_suelo> crearTipoSuelo;

    private final IModificarCasoUso<Tipo_suelo> modificarTipoSuelo;

    private final IEliminarCasoUso<Integer> eliminarTipoSueloPorId;

    @Override
    public Tipo_suelo crearObjeto(Tipo_suelo tipoSuelo) {
        return crearTipoSuelo.crearObjeto(tipoSuelo);
    }

    @Override
    public Integer eliminarObjeto(Integer idMaterial) {
        return eliminarTipoSueloPorId.eliminarObjeto(idMaterial);
    }

    @Override
    public Optional<Tipo_suelo> leerObjeto(Integer id) {
        return leerTipoSuelo.leerObjeto(id);
    }

    @Override
    public List<Tipo_suelo> leerObjetos() {
        return leerTipoSuelo.leerObjetos();
    }

    @Override
    public Optional<Tipo_suelo> modificarObjeto(Integer id,Tipo_suelo material) {
        return modificarTipoSuelo.modificarObjeto(id,material);
    }
}