package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ObraServicio implements ICrearCasoUso<Obra>, ILeerCasoUso<Obra>, IModificarCasoUso<Obra>, IEliminarCasoUso<Integer>{

    private final ILeerCasoUso<Obra> leerObra;

    private final ICrearCasoUso<Obra> crearObra;

    private final IModificarCasoUso<Obra> modificarObra;

    private final IEliminarCasoUso<Integer> eliminarObraPorId;

    @Override
    public Obra crearObjeto(Obra obra) {
        return crearObra.crearObjeto(obra);
    }

    @Override
    public Integer eliminarObjeto(Integer idObra) {
        return eliminarObraPorId.eliminarObjeto(idObra);
    }

    @Override
    public Optional<Obra> leerObjeto(Integer id) {
        return leerObra.leerObjeto(id);
    }

    @Override
    public List<Obra> leerObjetos() {
        return leerObra.leerObjetos();
    }

    @Override
    public Optional<Obra> modificarObjeto(Integer id,Obra Obra) {
        return modificarObra.modificarObjeto(id,Obra);
    }
}