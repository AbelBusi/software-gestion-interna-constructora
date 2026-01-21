package com.dev.CsiContratistas.application.CasoUso.Financiamiento;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearFinanciamientoCasoUsoImpl implements ICrearCasoUso<Financiamiento> {

    private final ObjectRepositorioPort<Financiamiento,Integer> financiamientoRepositorioPort;

    @Override
    public Financiamiento crearObjeto(Financiamiento financiamiento) {
        return financiamientoRepositorioPort.guardar(financiamiento);
    }

}