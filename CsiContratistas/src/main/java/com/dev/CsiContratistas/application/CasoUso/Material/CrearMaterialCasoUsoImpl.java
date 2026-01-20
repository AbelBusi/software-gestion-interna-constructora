package com.dev.CsiContratistas.application.CasoUso.Material;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearMaterialCasoUsoImpl implements ICrearCasoUso<Material> {


    private final ObjectRepositorioPort<Material,Integer> materialRepositorioPort;

    @Override
    public Material crearObjeto(Material material) {
        return materialRepositorioPort.guardar(material);
    }
}
