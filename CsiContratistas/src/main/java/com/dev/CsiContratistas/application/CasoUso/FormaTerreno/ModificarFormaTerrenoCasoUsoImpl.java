package com.dev.CsiContratistas.application.CasoUso.FormaTerreno;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarFormaTerrenoCasoUsoImpl implements IModificarCasoUso<Forma_terreno> {

    private final ObjectRepositorioPort<Forma_terreno,Integer> tipoSueloIntegerObjectRepositorioPort;

    @Override
    public Optional<Forma_terreno> modificarObjeto(Integer id,Forma_terreno formaTerreno) {
        return tipoSueloIntegerObjectRepositorioPort.modificar(id,formaTerreno);
    }

}