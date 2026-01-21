package com.dev.CsiContratistas.application.CasoUso.Empleado;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerEmpleadoCasoUsoImpl implements ILeerCasoUso<Empleado> {

    private final ObjectRepositorioPort<Empleado,Integer> empleadoObjectRepositorioPort;


    @Override
    public Optional<Empleado> leerObjeto(Integer id) {
        return empleadoObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Empleado> leerObjetos() {
        return empleadoObjectRepositorioPort.leerObjetos();
    }
}