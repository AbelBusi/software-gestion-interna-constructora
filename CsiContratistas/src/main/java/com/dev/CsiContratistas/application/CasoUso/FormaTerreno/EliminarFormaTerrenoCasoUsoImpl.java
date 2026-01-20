package com.dev.CsiContratistas.application.CasoUso.FormaTerreno;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarFormaTerrenoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Forma_terreno,Integer> formaTerrenoIntegerObjectRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idFormaTerreno) {
        return formaTerrenoIntegerObjectRepositorioPort.eliminar(idFormaTerreno);
    }
}