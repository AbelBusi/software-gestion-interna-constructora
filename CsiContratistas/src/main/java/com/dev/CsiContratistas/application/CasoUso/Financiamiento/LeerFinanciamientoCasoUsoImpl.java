package com.dev.CsiContratistas.application.CasoUso.Financiamiento;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerFinanciamientoCasoUsoImpl implements ILeerCasoUso<Financiamiento> {

    private final ObjectRepositorioPort<Financiamiento,Integer> financiamientoRepositorioPort;

    @Override
    public Optional<Financiamiento> leerObjeto(Integer id) {
        return financiamientoRepositorioPort.leer(id);
    }

    @Override
    public List<Financiamiento> leerObjetos() {
        return financiamientoRepositorioPort.leerObjetos();
    }
}