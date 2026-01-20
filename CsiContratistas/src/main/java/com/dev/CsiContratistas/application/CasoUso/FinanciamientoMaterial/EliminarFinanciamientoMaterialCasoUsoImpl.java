package com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarFinanciamientoMaterialCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Financiamiento_material,Integer> financiamientoMaterialRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idFinanciamientoMaterial) {
        return financiamientoMaterialRepositorioPort.eliminar(idFinanciamientoMaterial);
    }
}