package com.dev.CsiContratistas.application.CasoUso.Cliente;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarClienteCasoUsoImpl implements IModificarCasoUso<Cliente> {

    private final ObjectRepositorioPort<Cliente,Integer> clienteRepositorioPort;

    @Override
    public Optional<Cliente> modificarObjeto(Integer id,Cliente cliente) {
        return clienteRepositorioPort.modificar(id,cliente);
    }

}