package com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarFinanciamientoEmpleadoCasoUsoImpl implements IModificarCasoUso<Financiamiento_empleado> {

    private final ObjectRepositorioPort<Financiamiento_empleado,Integer> financiamientoRepositorioPort;

    @Override
    public Optional<Financiamiento_empleado> modificarObjeto(Integer id,Financiamiento_empleado financiamientoEmpleado) {
        return financiamientoRepositorioPort.modificar(id,financiamientoEmpleado);
    }

}