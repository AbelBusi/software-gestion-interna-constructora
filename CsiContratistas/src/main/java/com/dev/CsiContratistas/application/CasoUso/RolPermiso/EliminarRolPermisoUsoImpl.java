package com.dev.CsiContratistas.application.CasoUso.RolPermiso;

import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarRolPermisoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Rol_Permiso,Integer> rolPermisoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idRolPermiso) {
        return rolPermisoRepositorioPort.eliminar(idRolPermiso);
    }
}