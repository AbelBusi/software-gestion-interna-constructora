package com.dev.CsiContratistas.application.CasoUso.ModeloObra;

import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarModeloObraCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Modelo_obra,Integer> modeloObraRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idModeloObra) {
        return modeloObraRepositorioPort.eliminar(idModeloObra);
    }
}