package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ClienteServicio implements ICrearCasoUso<Cliente>, ILeerCasoUso<Cliente>, IModificarCasoUso<Cliente>, IEliminarCasoUso<Integer> {

    private final ILeerCasoUso<Cliente> leerCliente;


    private final ICrearCasoUso<Cliente> crearCliente;

    private final IModificarCasoUso<Cliente> modificarCliente;

    private final IEliminarCasoUso<Integer> eliminarClientePorId;

    @Override
    public Cliente crearObjeto(Cliente cliente) {
        return crearCliente.crearObjeto(cliente);
    }

    @Override
    public Integer eliminarObjeto(Integer idCliente) {
        return eliminarClientePorId.eliminarObjeto(idCliente);
    }

    @Override
    public Optional<Cliente> leerObjeto(Integer id) {
        return leerCliente.leerObjeto(id);
    }

    @Override
    public List<Cliente> leerObjetos() {
        return leerCliente.leerObjetos();
    }

    @Override
    public Optional<Cliente> modificarObjeto(Integer id,Cliente cliente) {
        return modificarCliente.modificarObjeto(id,cliente);
    }
}