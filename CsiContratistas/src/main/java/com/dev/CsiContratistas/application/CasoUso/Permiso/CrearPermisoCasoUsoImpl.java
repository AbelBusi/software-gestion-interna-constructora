package com.dev.CsiContratistas.application.CasoUso.Permiso;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearPermisoCasoUsoImpl implements ICrearCasoUso<Permiso> {

    private final ObjectRepositorioPort<Permiso,Integer> permisoRepositorioPort;

    @Override
    public Permiso crearObjeto(Permiso permiso) {
        return permisoRepositorioPort.guardar(permiso);
    }
}
