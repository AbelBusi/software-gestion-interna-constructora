package com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerFinanciamientoMaterialCasoUsoImpl implements ILeerCasoUso<Financiamiento_material> {

    private final ObjectRepositorioPort<Financiamiento_material,Integer> financiamientoMaterialRepositorioPort;

    @Override
    public Optional<Financiamiento_material> leerObjeto(Integer id) {
        return financiamientoMaterialRepositorioPort.leer(id);
    }

    @Override
    public List<Financiamiento_material> leerObjetos() {
        return financiamientoMaterialRepositorioPort.leerObjetos();
    }
}