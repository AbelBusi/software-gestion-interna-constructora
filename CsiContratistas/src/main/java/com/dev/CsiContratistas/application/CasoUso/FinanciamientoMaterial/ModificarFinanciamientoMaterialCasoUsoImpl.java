package com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarFinanciamientoMaterialCasoUsoImpl implements IModificarCasoUso<Financiamiento_material> {

    private final ObjectRepositorioPort<Financiamiento_material,Integer> financiamientoRepositorioPort;

    @Override
    public Optional<Financiamiento_material> modificarObjeto(Integer id,Financiamiento_material financiamientoMaterial) {
        return financiamientoRepositorioPort.modificar(id,financiamientoMaterial);
    }

}