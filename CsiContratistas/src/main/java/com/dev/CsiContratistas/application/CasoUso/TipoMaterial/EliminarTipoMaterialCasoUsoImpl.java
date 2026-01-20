package com.dev.CsiContratistas.application.CasoUso.TipoMaterial;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarTipoMaterialCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Tipo_material,Integer> tipoMaterialRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idMaterial) {
        return tipoMaterialRepositorioPort.eliminar(idMaterial);
    }
}