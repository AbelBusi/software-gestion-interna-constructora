package com.dev.CsiContratistas.application.CasoUso.TipoMaterial;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerTipoMaterialCasoUsoImpl implements ILeerCasoUso<Tipo_material> {

    private final ObjectRepositorioPort<Tipo_material,Integer> tipoMaterialIntegerObjectRepositorioPort;


    @Override
    public Optional<Tipo_material> leerObjeto(Integer id) {
        return tipoMaterialIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Tipo_material> leerObjetos() {
        return tipoMaterialIntegerObjectRepositorioPort.leerObjetos();
    }
}