package com.dev.CsiContratistas.application.CasoUso.Permiso;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarPermisoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Permiso,Integer> permisoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idPermiso) {
        return permisoRepositorioPort.eliminar(idPermiso);
    }
}