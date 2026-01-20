package com.dev.CsiContratistas.application.CasoUso.Empleado;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarEmpleadoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Empleado,Integer> permisoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idEmpleado) {
        return permisoRepositorioPort.eliminar(idEmpleado);
    }
}