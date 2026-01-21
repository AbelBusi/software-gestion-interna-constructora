package com.dev.CsiContratistas.application.CasoUso.Cliente;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor

public class CrearClienteCasoUsoImpl implements ICrearCasoUso<Cliente> {

    private final ObjectRepositorioPort<Cliente,Integer> clienteRepositorioPort;

    @Override
    public Cliente crearObjeto(Cliente cliente) {
        return clienteRepositorioPort.guardar(cliente);
    }

}