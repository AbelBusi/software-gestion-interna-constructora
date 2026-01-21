package com.dev.CsiContratistas.application.CasoUso.Cliente;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarClienteCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Cliente,Integer> clienteRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idCliente) {
        return clienteRepositorioPort.eliminar(idCliente);
    }
}