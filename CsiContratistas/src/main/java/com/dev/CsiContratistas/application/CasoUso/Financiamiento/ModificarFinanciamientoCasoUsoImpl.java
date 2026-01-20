package com.dev.CsiContratistas.application.CasoUso.Financiamiento;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarFinanciamientoCasoUsoImpl implements IModificarCasoUso<Financiamiento> {

    private final ObjectRepositorioPort<Financiamiento,Integer> financiamientoRepositorioPort;

    @Override
    public Optional<Financiamiento> modificarObjeto(Integer id,Financiamiento financiamiento) {
        return financiamientoRepositorioPort.modificar(id,financiamiento);
    }

}