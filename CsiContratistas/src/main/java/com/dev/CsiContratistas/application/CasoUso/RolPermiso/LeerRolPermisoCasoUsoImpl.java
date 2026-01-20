package com.dev.CsiContratistas.application.CasoUso.RolPermiso;

import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerRolPermisoCasoUsoImpl implements ILeerCasoUso<Rol_Permiso> {

    private final ObjectRepositorioPort<Rol_Permiso,Integer> rolPermisoIntegerObjectRepositorioPort;



    @Override
    public Optional<Rol_Permiso> leerObjeto(Integer id) {
        return rolPermisoIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Rol_Permiso> leerObjetos() {
        return rolPermisoIntegerObjectRepositorioPort.leerObjetos();
    }
}