package com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearFinanciamientoEmpleadoCasoUsoImpl implements ICrearCasoUso<Financiamiento_empleado> {

    private final ObjectRepositorioPort<Financiamiento_empleado,Integer> financiamientoEmpleadoRepositorioPort;

    @Override
    public Financiamiento_empleado crearObjeto(Financiamiento_empleado financiamientoEmpleado) {
        return financiamientoEmpleadoRepositorioPort.guardar(financiamientoEmpleado);
    }

}