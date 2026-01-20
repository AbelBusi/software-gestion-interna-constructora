package com.dev.CsiContratistas.application.CasoUso.obras;

import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarObraCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Obra,Integer> obraRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idObra) {
        return obraRepositorioPort.eliminar(idObra);
    }
}