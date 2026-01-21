package com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarFinanciamientoEmpleadoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Financiamiento_empleado,Integer> financiamientoEmpleadoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idFinanciamientoMaterial) {
        return financiamientoEmpleadoRepositorioPort.eliminar(idFinanciamientoMaterial);
    }
}