package com.dev.CsiContratistas.application.CasoUso.Usuario;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearUsuarioCasoUsoImpl implements ICrearCasoUso<Usuario> {

    private final ObjectRepositorioPort<Usuario,Integer> usuarioRepositorioPort;

    @Override
    public Usuario crearObjeto(Usuario usuario) {
        return usuarioRepositorioPort.guardar(usuario);
    }
}
