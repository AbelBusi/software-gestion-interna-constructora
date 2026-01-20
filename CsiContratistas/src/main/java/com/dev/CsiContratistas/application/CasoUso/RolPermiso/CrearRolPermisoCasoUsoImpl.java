package com.dev.CsiContratistas.application.CasoUso.RolPermiso;

import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearRolPermisoCasoUsoImpl implements ICrearCasoUso<Rol_Permiso> {

    private final ObjectRepositorioPort<Rol_Permiso,Integer> rolPermisoRepositorioPort;

    @Override
    public Rol_Permiso crearObjeto(Rol_Permiso rolPermiso) {
        return rolPermisoRepositorioPort.guardar(rolPermiso);
    }
}
