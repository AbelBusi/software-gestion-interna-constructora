package com.dev.CsiContratistas.application.CasoUso.RolPermiso;

import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarRolPermisoCasoUsoImpl implements IModificarCasoUso<Rol_Permiso> {

    private final ObjectRepositorioPort<Rol_Permiso,Integer> rolPermisoRepositorioPort;

    @Override
    public Optional<Rol_Permiso> modificarObjeto(Integer id,Rol_Permiso rolPermiso) {
        return rolPermisoRepositorioPort.modificar(id,rolPermiso);
    }

}