package com.dev.CsiContratistas.application.CasoUso.obras;

import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarObraCasoUsoImpl implements IModificarCasoUso<Obra> {

    private final ObjectRepositorioPort<Obra,Integer> obraIntegerObjectRepositorioPort;

        @Override
    public Optional<Obra> modificarObjeto(Integer id,Obra obra) {
        return obraIntegerObjectRepositorioPort.modificar(id,obra);
    }

}