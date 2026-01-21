package com.dev.CsiContratistas.application.CasoUso.Empleado;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.ports.in.IVerificarObjetoCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectValidRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class VerificarEmpleadoCasoUsoImpl implements IVerificarObjetoCasoUso<String> {

    private final ObjectValidRepositorioPort<String> empleadoRepositorioPort;

    @Override
    public boolean obtenerPorParametro(String parametro) {

        return empleadoRepositorioPort.leerParametro(parametro);

    }

}