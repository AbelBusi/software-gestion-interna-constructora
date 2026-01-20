package com.dev.CsiContratistas.application.CasoUso.RolUsuario;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarRolUsuarioCasoUsoImpl implements IModificarCasoUso<Rol_usuario> {

    private final ObjectRepositorioPort<Rol_usuario,Integer> rolUsuarioRepositorioPort;

    @Override
    public Optional<Rol_usuario> modificarObjeto(Integer id,Rol_usuario rolUsuario) {
        return rolUsuarioRepositorioPort.modificar(id,rolUsuario);
    }

}