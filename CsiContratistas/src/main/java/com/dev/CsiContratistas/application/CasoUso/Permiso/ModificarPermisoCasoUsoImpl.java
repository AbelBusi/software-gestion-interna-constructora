package com.dev.CsiContratistas.application.CasoUso.Permiso;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarPermisoCasoUsoImpl implements IModificarCasoUso<Permiso> {

    private final ObjectRepositorioPort<Permiso,Integer> permisoRepositorioPort;

    @Override
    public Optional<Permiso> modificarObjeto(Integer id,Permiso permiso) {
        return permisoRepositorioPort.modificar(id,permiso);
    }

}