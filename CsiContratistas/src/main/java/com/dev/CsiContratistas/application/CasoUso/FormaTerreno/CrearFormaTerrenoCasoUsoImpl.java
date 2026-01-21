package com.dev.CsiContratistas.application.CasoUso.FormaTerreno;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearFormaTerrenoCasoUsoImpl implements ICrearCasoUso<Forma_terreno> {

    private final ObjectRepositorioPort<Forma_terreno,Integer> formaTerrenoRepositorioPort;

    @Override
    public Forma_terreno crearObjeto(Forma_terreno formaTerreno) {
        return formaTerrenoRepositorioPort.guardar(formaTerreno);
    }
}
