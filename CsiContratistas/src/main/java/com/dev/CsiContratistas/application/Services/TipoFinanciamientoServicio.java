package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TipoFinanciamientoServicio implements ICrearCasoUso<Tipo_financiamiento>, ILeerCasoUso<Tipo_financiamiento>, IModificarCasoUso<Tipo_financiamiento>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Tipo_financiamiento> leerTipoFinanciamiento;

    private final ICrearCasoUso<Tipo_financiamiento> crearTipoFinanciamiento;

    private final IModificarCasoUso<Tipo_financiamiento> modificarTipoFinanciamiento;

    private final IEliminarCasoUso<Integer> eliminarTipoFinanciamientoPorId;

    @Override
    public Tipo_financiamiento crearObjeto(Tipo_financiamiento tipoFinanciamiento) {
        return crearTipoFinanciamiento.crearObjeto(tipoFinanciamiento);
    }

    @Override
    public Integer eliminarObjeto(Integer idTipoFinaciamiento) {
        return eliminarTipoFinanciamientoPorId.eliminarObjeto(idTipoFinaciamiento);
    }

    @Override
    public Optional<Tipo_financiamiento> leerObjeto(Integer id) {
        return leerTipoFinanciamiento.leerObjeto(id);
    }

    @Override
    public List<Tipo_financiamiento> leerObjetos() {
        return leerTipoFinanciamiento.leerObjetos();
    }

    @Override
    public Optional<Tipo_financiamiento> modificarObjeto(Integer id,Tipo_financiamiento tipoFinanciamiento) {
        return modificarTipoFinanciamiento.modificarObjeto(id,tipoFinanciamiento);
    }
}