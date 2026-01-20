package com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearFinanciamientoMaterialCasoUsoImpl implements ICrearCasoUso<Financiamiento_material> {

    private final ObjectRepositorioPort<Financiamiento_material,Integer> financiamientoMaterialRepositorioPort;

    @Override
    public Financiamiento_material crearObjeto(Financiamiento_material financiamiento) {
        return financiamientoMaterialRepositorioPort.guardar(financiamiento);
    }

}