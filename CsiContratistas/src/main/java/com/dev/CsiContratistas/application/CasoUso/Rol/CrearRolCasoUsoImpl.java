package com.dev.CsiContratistas.application.CasoUso.Rol;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearRolCasoUsoImpl implements ICrearCasoUso<Rol> {

    private final ObjectRepositorioPort<Rol,Integer> rolRepositorioPort;

    @Override
    public Rol crearObjeto(Rol rol) {
        return rolRepositorioPort.guardar(rol);
    }
}
