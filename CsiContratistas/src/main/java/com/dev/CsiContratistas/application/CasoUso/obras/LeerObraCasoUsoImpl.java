package com.dev.CsiContratistas.application.CasoUso.obras;

import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerObraCasoUsoImpl implements ILeerCasoUso<Obra> {

    private final ObjectRepositorioPort<Obra,Integer> obraIntegerObjectRepositorioPort;

    @Override
    public Optional<Obra> leerObjeto(Integer id) {
        return obraIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Obra> leerObjetos() {
        return obraIntegerObjectRepositorioPort.leerObjetos();
    }
}