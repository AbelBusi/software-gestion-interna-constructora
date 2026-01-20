package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TerrenoServicio implements ICrearCasoUso<Terreno>, ILeerCasoUso<Terreno>, IModificarCasoUso<Terreno>, IEliminarCasoUso<Integer>{

    private final ILeerCasoUso<Terreno> leerTerreno;

    private final ICrearCasoUso<Terreno> crearTerreno;

    private final IModificarCasoUso<Terreno> modificarTerreno;

    private final IEliminarCasoUso<Integer> eliminarTerrenoPorId;

    @Override
    public Terreno crearObjeto(Terreno terreno) {
        return crearTerreno.crearObjeto(terreno);
    }

    @Override
    public Integer eliminarObjeto(Integer idTerreno) {
        return eliminarTerrenoPorId.eliminarObjeto(idTerreno);
    }

    @Override
    public Optional<Terreno> leerObjeto(Integer id) {
        return leerTerreno.leerObjeto(id);
    }

    @Override
    public List<Terreno> leerObjetos() {
        return leerTerreno.leerObjetos();
    }

    @Override
    public Optional<Terreno> modificarObjeto(Integer id,Terreno terreno) {
        return modificarTerreno.modificarObjeto(id,terreno);
    }
}