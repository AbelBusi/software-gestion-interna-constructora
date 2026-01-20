package com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerFinanciamientoEmpleadoCasoUsoImpl implements ILeerCasoUso<Financiamiento_empleado> {

    private final ObjectRepositorioPort<Financiamiento_empleado,Integer> financiamientoEmpleadoRepositorioPort;

    @Override
    public Optional<Financiamiento_empleado> leerObjeto(Integer id) {
        return financiamientoEmpleadoRepositorioPort.leer(id);
    }

    @Override
    public List<Financiamiento_empleado> leerObjetos() {
        return financiamientoEmpleadoRepositorioPort.leerObjetos();
    }
}