package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class FinanciamientoServicio implements ICrearCasoUso<Financiamiento>, ILeerCasoUso<Financiamiento>, IModificarCasoUso<Financiamiento>, IEliminarCasoUso<Integer> {
    private final ILeerCasoUso<Financiamiento> leerFinanciamiento;

    private final ICrearCasoUso<Financiamiento> crearFinanciamiento;

    private final IModificarCasoUso<Financiamiento> modificarFinanciamiento;

    private final IEliminarCasoUso<Integer> eliminarFinanciamientoPorId;

    @Override
    public Financiamiento crearObjeto(Financiamiento financiamiento) {
        return crearFinanciamiento.crearObjeto(financiamiento);
    }

    @Override
    public Integer eliminarObjeto(Integer idFinanciamiento) {
        return eliminarFinanciamientoPorId.eliminarObjeto(idFinanciamiento);
    }

    @Override
    public Optional<Financiamiento> leerObjeto(Integer id) {
        return leerFinanciamiento.leerObjeto(id);
    }

    @Override
    public List<Financiamiento> leerObjetos() {
        return leerFinanciamiento.leerObjetos();
    }

    @Override
    public Optional<Financiamiento> modificarObjeto(Integer id,Financiamiento financiamiento) {
        return modificarFinanciamiento.modificarObjeto(id,financiamiento);
    }
}