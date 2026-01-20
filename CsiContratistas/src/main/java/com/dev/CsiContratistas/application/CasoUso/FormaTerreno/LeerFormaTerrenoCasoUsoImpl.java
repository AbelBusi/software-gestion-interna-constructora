package com.dev.CsiContratistas.application.CasoUso.FormaTerreno;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerFormaTerrenoCasoUsoImpl implements ILeerCasoUso<Forma_terreno> {

    private final ObjectRepositorioPort<Forma_terreno,Integer> formaTerrenoIntegerObjectRepositorioPort;

    @Override
    public Optional<Forma_terreno> leerObjeto(Integer id) {
        return formaTerrenoIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Forma_terreno> leerObjetos() {
        return formaTerrenoIntegerObjectRepositorioPort.leerObjetos();
    }
}