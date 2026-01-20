package com.dev.CsiContratistas.application.CasoUso.obras;

import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearObraCasoUsoImpl implements ICrearCasoUso<Obra> {


    private final ObjectRepositorioPort<Obra,Integer> obraRepositorioPort;

    @Override
    public Obra crearObjeto(Obra obra) {
        return obraRepositorioPort.guardar(obra);
    }
}
