package com.dev.CsiContratistas.application.CasoUso.Usuario;

import com.dev.CsiContratistas.domain.ports.in.IVerificarObjetoCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectValidRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class VerificarUsuarioCasoUsoImpl implements IVerificarObjetoCasoUso<String> {

    private final ObjectValidRepositorioPort<String> usuarioRepositorioPort;

    @Override
    public boolean obtenerPorParametro(String parametro) {

        return usuarioRepositorioPort.leerParametro(parametro);

    }

}