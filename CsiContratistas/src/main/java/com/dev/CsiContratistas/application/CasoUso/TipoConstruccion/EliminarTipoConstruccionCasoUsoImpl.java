package com.dev.CsiContratistas.application.CasoUso.TipoConstruccion;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarTipoConstruccionCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Tipo_construccion,Integer> tipoConstruccionIntegerObjectRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idTipoConstruccion) {
        return tipoConstruccionIntegerObjectRepositorioPort.eliminar(idTipoConstruccion);
    }
}