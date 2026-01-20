package com.dev.CsiContratistas.application.CasoUso.TipoMaterial;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearTipoMaterialCasoUsoImpl implements ICrearCasoUso<Tipo_material> {

    private final ObjectRepositorioPort<Tipo_material,Integer> materialTipoRepositorioPort;

    @Override
    public Tipo_material crearObjeto(Tipo_material tipoMaterial) {
        return materialTipoRepositorioPort.guardar(tipoMaterial);
    }
}
