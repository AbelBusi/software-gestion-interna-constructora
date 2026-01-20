package com.dev.CsiContratistas.application.CasoUso.Profesion;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarProfesionCasoUsoImpl implements IModificarCasoUso<Profesiones> {

    private final ObjectRepositorioPort<Profesiones,Integer> permisoRepositorioPort;

    @Override
    public Optional<Profesiones> modificarObjeto(Integer id,Profesiones profesiones) {
        return permisoRepositorioPort.modificar(id,profesiones);
    }

}