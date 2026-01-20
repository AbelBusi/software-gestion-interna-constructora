package com.dev.CsiContratistas.application.CasoUso.Terreno;

import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarTerrenoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Terreno,Integer> terrenoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer terreno) {
        return terrenoRepositorioPort.eliminar(terreno);
    }
}