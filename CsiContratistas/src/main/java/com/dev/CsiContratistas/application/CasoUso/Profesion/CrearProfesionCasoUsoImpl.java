package com.dev.CsiContratistas.application.CasoUso.Profesion;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearProfesionCasoUsoImpl implements ICrearCasoUso<Profesiones> {

    private final ObjectRepositorioPort<Profesiones,Integer> profesionesRepositorioPort;

    @Override
    public Profesiones crearObjeto(Profesiones profesiones) {
        return profesionesRepositorioPort.guardar(profesiones);
    }
}
