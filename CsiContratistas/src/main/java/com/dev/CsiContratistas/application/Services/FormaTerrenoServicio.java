package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FormaTerrenoServicio implements ICrearCasoUso<Forma_terreno>, ILeerCasoUso<Forma_terreno>, IModificarCasoUso<Forma_terreno>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Forma_terreno> leerFormaTerreno;

    private final ICrearCasoUso<Forma_terreno> crearFormaTerreno;

    private final IModificarCasoUso<Forma_terreno> modificarFormaTerreno;

    private final IEliminarCasoUso<Integer> eliminarFormaTerrenoPorId;

    @Override
    public Forma_terreno crearObjeto(Forma_terreno formaTerreno) {
        return crearFormaTerreno.crearObjeto(formaTerreno);
    }

    @Override
    public Integer eliminarObjeto(Integer idFormaTerreno) {
        return eliminarFormaTerrenoPorId.eliminarObjeto(idFormaTerreno);
    }

    @Override
    public Optional<Forma_terreno> leerObjeto(Integer id) {
        return leerFormaTerreno.leerObjeto(id);
    }

    @Override
    public List<Forma_terreno> leerObjetos() {
        return leerFormaTerreno.leerObjetos();
    }

    @Override
    public Optional<Forma_terreno> modificarObjeto(Integer id,Forma_terreno formaTerreno) {
        return modificarFormaTerreno.modificarObjeto(id,formaTerreno);
    }
}