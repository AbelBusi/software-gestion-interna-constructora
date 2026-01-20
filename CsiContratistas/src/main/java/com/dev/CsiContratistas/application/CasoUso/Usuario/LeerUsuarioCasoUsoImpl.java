package com.dev.CsiContratistas.application.CasoUso.Usuario;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerUsuarioCasoUsoImpl implements ILeerCasoUso<Usuario> {

    private final ObjectRepositorioPort<Usuario,Integer> usuarioObjectRepositorioPort;


    @Override
    public Optional<Usuario> leerObjeto(Integer id) {
        return usuarioObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Usuario> leerObjetos() {
        return usuarioObjectRepositorioPort.leerObjetos();
    }
}