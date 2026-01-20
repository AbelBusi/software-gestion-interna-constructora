package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TipoConstruccionServicio implements ICrearCasoUso<Tipo_construccion>, ILeerCasoUso<Tipo_construccion>, IModificarCasoUso<Tipo_construccion>, IEliminarCasoUso<Integer>{

    private final ILeerCasoUso<Tipo_construccion> leerTipoConstruccion;

    private final ICrearCasoUso<Tipo_construccion> crearTipoConstruccion;

    private final IModificarCasoUso<Tipo_construccion> modificarTipoConstruccion;

    private final IEliminarCasoUso<Integer> eliminarTipoConstruccionPorId;

    @Override
    public Tipo_construccion crearObjeto(Tipo_construccion tipoConstruccion) {
        return crearTipoConstruccion.crearObjeto(tipoConstruccion);
    }

    @Override
    public Integer eliminarObjeto(Integer idTipoConstruccion) {
        return eliminarTipoConstruccionPorId.eliminarObjeto(idTipoConstruccion);
    }

    @Override
    public Optional<Tipo_construccion> leerObjeto(Integer id) {
        return leerTipoConstruccion.leerObjeto(id);
    }

    @Override
    public List<Tipo_construccion> leerObjetos() {
        return leerTipoConstruccion.leerObjetos();
    }

    @Override
    public Optional<Tipo_construccion> modificarObjeto(Integer id,Tipo_construccion tipoConstruccion) {
        return modificarTipoConstruccion.modificarObjeto(id,tipoConstruccion);
    }
}