package com.dev.CsiContratistas.application.CasoUso.Empleado;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarEmpleadoCasoUsoImpl implements IModificarCasoUso<Empleado> {

    private final ObjectRepositorioPort<Empleado,Integer> empleadoRepositorioPort;

    @Override
    public Optional<Empleado> modificarObjeto(Integer id,Empleado empleado) {
        return empleadoRepositorioPort.modificar(id,empleado);
    }

}