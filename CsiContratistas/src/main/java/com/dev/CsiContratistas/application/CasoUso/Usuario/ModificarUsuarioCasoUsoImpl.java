package com.dev.CsiContratistas.application.CasoUso.Usuario;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarUsuarioCasoUsoImpl implements IModificarCasoUso<Usuario> {

    private final ObjectRepositorioPort<Usuario,Integer> usuarioRepositorioPort;

    @Override
    public Optional<Usuario> modificarObjeto(Integer id,Usuario usuario) {
        return usuarioRepositorioPort.modificar(id,usuario);
    }

}