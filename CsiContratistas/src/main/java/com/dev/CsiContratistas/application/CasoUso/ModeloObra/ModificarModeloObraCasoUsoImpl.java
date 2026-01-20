package com.dev.CsiContratistas.application.CasoUso.ModeloObra;

import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarModeloObraCasoUsoImpl implements IModificarCasoUso<Modelo_obra> {


    private final ObjectRepositorioPort<Modelo_obra,Integer> modeloObraIntegerObjectRepositorioPort;

        @Override
    public Optional<Modelo_obra> modificarObjeto(Integer id,Modelo_obra modeloObra) {
        return modeloObraIntegerObjectRepositorioPort.modificar(id,modeloObra);
    }

}