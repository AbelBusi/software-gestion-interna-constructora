package com.dev.CsiContratistas.application.CasoUso.Permiso;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerPermisoCasoUsoImpl implements ILeerCasoUso<Permiso> {

    private final ObjectRepositorioPort<Permiso,Integer> permisoObjectRepositorioPort;


    @Override
    public Optional<Permiso> leerObjeto(Integer id) {
        return permisoObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Permiso> leerObjetos() {
        return permisoObjectRepositorioPort.leerObjetos();
    }
}