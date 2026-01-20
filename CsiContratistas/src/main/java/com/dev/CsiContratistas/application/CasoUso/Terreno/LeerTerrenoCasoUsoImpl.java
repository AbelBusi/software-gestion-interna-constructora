package com.dev.CsiContratistas.application.CasoUso.Terreno;

import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerTerrenoCasoUsoImpl implements ILeerCasoUso<Terreno> {

    private final ObjectRepositorioPort<Terreno,Integer> terrenoIntegerObjectRepositorioPort;


    @Override
    public Optional<Terreno> leerObjeto(Integer id) {
        return terrenoIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Terreno> leerObjetos() {
        return terrenoIntegerObjectRepositorioPort.leerObjetos();
    }
}