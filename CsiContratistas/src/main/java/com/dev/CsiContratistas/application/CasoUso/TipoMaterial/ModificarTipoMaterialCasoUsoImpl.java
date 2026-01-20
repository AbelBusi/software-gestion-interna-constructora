package com.dev.CsiContratistas.application.CasoUso.TipoMaterial;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarTipoMaterialCasoUsoImpl implements IModificarCasoUso<Tipo_material> {

    private final ObjectRepositorioPort<Tipo_material,Integer> tipoMaterialRepositorioPort;

    @Override
    public Optional<Tipo_material> modificarObjeto(Integer id,Tipo_material tipoMaterial) {
        return tipoMaterialRepositorioPort.modificar(id,tipoMaterial);
    }

}