package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FinanciamientoMaterialServicio implements ICrearCasoUso<Financiamiento_material>, ILeerCasoUso<Financiamiento_material>, IModificarCasoUso<Financiamiento_material>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Financiamiento_material> leerFinanciamientoMaterial;

    private final ICrearCasoUso<Financiamiento_material> crearFinanciamientoMaterial;

    private final IModificarCasoUso<Financiamiento_material> modificarFinanciamientoMaterial;

    private final IEliminarCasoUso<Integer> eliminarFinanciamientoMaterialPorId;

    @Override
    public Financiamiento_material crearObjeto(Financiamiento_material financiamientoMaterial) {
        return crearFinanciamientoMaterial.crearObjeto(financiamientoMaterial);
    }

    @Override
    public Integer eliminarObjeto(Integer idFinanciamientoMaterial) {
        return eliminarFinanciamientoMaterialPorId.eliminarObjeto(idFinanciamientoMaterial);
    }

    @Override
    public Optional<Financiamiento_material> leerObjeto(Integer id) {
        return leerFinanciamientoMaterial.leerObjeto(id);
    }

    @Override
    public List<Financiamiento_material> leerObjetos() {
        return leerFinanciamientoMaterial.leerObjetos();
    }

    @Override
    public Optional<Financiamiento_material> modificarObjeto(Integer id,Financiamiento_material financiamientoMaterial) {
        return modificarFinanciamientoMaterial.modificarObjeto(id,financiamientoMaterial);
    }
}