package com.dev.CsiContratistas.application.CasoUso.Cliente;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerClienteCasoUsoImpl implements ILeerCasoUso<Cliente> {

    private final ObjectRepositorioPort<Cliente,Integer> clienteObjectRepositorioPort;


    @Override
    public Optional<Cliente> leerObjeto(Integer id) {
        return clienteObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Cliente> leerObjetos() {
        return clienteObjectRepositorioPort.leerObjetos();
    }
}