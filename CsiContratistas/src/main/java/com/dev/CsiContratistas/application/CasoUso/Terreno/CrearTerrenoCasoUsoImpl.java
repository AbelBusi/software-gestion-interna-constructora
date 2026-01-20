package com.dev.CsiContratistas.application.CasoUso.Terreno;

import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearTerrenoCasoUsoImpl implements ICrearCasoUso<Terreno> {

    private final ObjectRepositorioPort<Terreno,Integer> terrenoTipoRepositorioPort;

    @Override
    public Terreno crearObjeto(Terreno terreno) {
        return terrenoTipoRepositorioPort.guardar(terreno);
    }
}
