package com.dev.CsiContratistas.application.CasoUso.Terreno;

import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarTerrenoCasoUsoImpl implements IModificarCasoUso<Terreno> {

    private final ObjectRepositorioPort<Terreno,Integer> terrenoIntegerObjectRepositorioPort;

    @Override
    public Optional<Terreno> modificarObjeto(Integer id,Terreno terreno) {
        return terrenoIntegerObjectRepositorioPort.modificar(id,terreno);
    }

}