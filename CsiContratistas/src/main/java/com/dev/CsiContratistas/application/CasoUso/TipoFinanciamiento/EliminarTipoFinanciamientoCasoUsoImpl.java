package com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarTipoFinanciamientoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Tipo_financiamiento,Integer> tipoFinanciamientoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idTipoFinanciamiento) {
        return tipoFinanciamientoRepositorioPort.eliminar(idTipoFinanciamiento);
    }
}