package com.dev.CsiContratistas.application.CasoUso.Material;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarMaterialCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Material,Integer> materialRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idMaterial) {
        return materialRepositorioPort.eliminar(idMaterial);
    }
}