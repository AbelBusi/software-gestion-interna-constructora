package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FinanciamientoEmpleadoServicio implements ICrearCasoUso<Financiamiento_empleado>, ILeerCasoUso<Financiamiento_empleado>, IModificarCasoUso<Financiamiento_empleado>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Financiamiento_empleado> leerFinanciamientoEmpleado;

    private final ICrearCasoUso<Financiamiento_empleado> crearFinanciamientoEmpleado;

    private final IModificarCasoUso<Financiamiento_empleado> modificarFinanciamientoEmpleado;

    private final IEliminarCasoUso<Integer> eliminarFinanciamientoEmpleadoPorId;

    @Override
    public Financiamiento_empleado crearObjeto(Financiamiento_empleado financiamientoEmpleado) {
        return crearFinanciamientoEmpleado.crearObjeto(financiamientoEmpleado);
    }

    @Override
    public Integer eliminarObjeto(Integer idFinanciamientoEmpleado) {
        return eliminarFinanciamientoEmpleadoPorId.eliminarObjeto(idFinanciamientoEmpleado);
    }

    @Override
    public Optional<Financiamiento_empleado> leerObjeto(Integer id) {
        return leerFinanciamientoEmpleado.leerObjeto(id);
    }

    @Override
    public List<Financiamiento_empleado> leerObjetos() {
        return leerFinanciamientoEmpleado.leerObjetos();
    }

    @Override
    public Optional<Financiamiento_empleado> modificarObjeto(Integer id,Financiamiento_empleado financiamientoEmpleado) {
        return modificarFinanciamientoEmpleado.modificarObjeto(id,financiamientoEmpleado);
    }
}