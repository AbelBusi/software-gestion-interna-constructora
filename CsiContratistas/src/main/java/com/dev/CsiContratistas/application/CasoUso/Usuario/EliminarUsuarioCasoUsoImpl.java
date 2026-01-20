package com.dev.CsiContratistas.application.CasoUso.Usuario;

import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarUsuarioCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Usuario,Integer> usuarioRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idUsuario) {
        return usuarioRepositorioPort.eliminar(idUsuario);
    }
}