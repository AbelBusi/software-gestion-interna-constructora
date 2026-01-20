package com.dev.CsiContratistas.application.CasoUso.Material;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarMaterialCasoUsoImpl implements IModificarCasoUso<Material> {


    private final ObjectRepositorioPort<Material,Integer> materialRepositorioPort;

    @Override
    public Optional<Material> modificarObjeto(Integer id,Material material) {
        return materialRepositorioPort.modificar(id,material);
    }

}