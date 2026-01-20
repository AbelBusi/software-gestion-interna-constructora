package com.dev.CsiContratistas.application.CasoUso.TipoObra;

import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarTipoObraCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Tipo_obra,Integer> tipoObraIntegerObjectRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idRamal) {
        return tipoObraIntegerObjectRepositorioPort.eliminar(idRamal);
    }
}