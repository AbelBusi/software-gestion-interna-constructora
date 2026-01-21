package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ModeloObraServicio implements ICrearCasoUso<Modelo_obra>, ILeerCasoUso<Modelo_obra>, IModificarCasoUso<Modelo_obra>, IEliminarCasoUso<Integer>{

    private final ILeerCasoUso<Modelo_obra> leerModeloObra;

    private final ICrearCasoUso<Modelo_obra> crearModeloObra;

    private final IModificarCasoUso<Modelo_obra> modificarModeloObra;

    private final IEliminarCasoUso<Integer> eliminarModeloObraPorId;

    @Override
    public Modelo_obra crearObjeto(Modelo_obra modeloObra) {
        return crearModeloObra.crearObjeto(modeloObra);
    }

    @Override
    public Integer eliminarObjeto(Integer idModeloObra) {
        return eliminarModeloObraPorId.eliminarObjeto(idModeloObra);
    }

    @Override
    public Optional<Modelo_obra> leerObjeto(Integer id) {
        return leerModeloObra.leerObjeto(id);
    }

    @Override
    public List<Modelo_obra> leerObjetos() {
        return leerModeloObra.leerObjetos();
    }

    @Override
    public Optional<Modelo_obra> modificarObjeto(Integer id,Modelo_obra modeloObra) {
        return modificarModeloObra.modificarObjeto(id,modeloObra);
    }
}