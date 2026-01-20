package com.dev.CsiContratistas.application.CasoUso.Financiamiento;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarFinanciamientoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Financiamiento,Integer> financiamientoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idFinanciamiento) {
        return financiamientoRepositorioPort.eliminar(idFinanciamiento);
    }
}