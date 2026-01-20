package com.dev.CsiContratistas.application.CasoUso.TipoSuelo;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarTipoSueloCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Tipo_suelo,Integer> tipoObraIntegerObjectRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idTipoSuelo) {
        return tipoObraIntegerObjectRepositorioPort.eliminar(idTipoSuelo);
    }
}