package com.dev.CsiContratistas.application.CasoUso.Empleado;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearEmpleadoCasoUsoImpl implements ICrearCasoUso<Empleado> {

    private final ObjectRepositorioPort<Empleado,Integer> empleadoRepositorioPort;

    @Override
    public Empleado crearObjeto(Empleado empleado) {
        return empleadoRepositorioPort.guardar(empleado);
    }
}
