package com.dev.CsiContratistas.application.CasoUso.Material;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerMaterialCasoUsoImpl implements ILeerCasoUso<Material> {

    private final ObjectRepositorioPort<Material,Integer> materialIntegerObjectRepositorioPort;


    @Override
    public Optional<Material> leerObjeto(Integer id) {
        return materialIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Material> leerObjetos() {
        return materialIntegerObjectRepositorioPort.leerObjetos();
    }
}