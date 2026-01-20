package com.dev.CsiContratistas.application.CasoUso.RolUsuario;

import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarRolUsuarioUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Rol_usuario,Integer> rolUsuarioRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idRolUsuario) {
        return rolUsuarioRepositorioPort.eliminar(idRolUsuario);
    }
}