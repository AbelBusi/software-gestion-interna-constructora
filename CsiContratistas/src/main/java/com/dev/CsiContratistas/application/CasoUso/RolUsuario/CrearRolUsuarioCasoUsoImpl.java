package com.dev.CsiContratistas.application.CasoUso.RolUsuario;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearRolUsuarioCasoUsoImpl implements ICrearCasoUso<Rol_usuario> {

    private final ObjectRepositorioPort<Rol_usuario,Integer> rolUsuarioRepositorioPort;

    @Override
    public Rol_usuario crearObjeto(Rol_usuario rolUsuario) {
        return rolUsuarioRepositorioPort.guardar(rolUsuario);
    }
}
