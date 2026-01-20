package com.dev.CsiContratistas.application.CasoUso.ModeloObra;

import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearModeloObraCasoUsoImpl implements ICrearCasoUso<Modelo_obra> {


    private final ObjectRepositorioPort<Modelo_obra,Integer> modeloObraRepositorioPort;

    @Override
    public Modelo_obra crearObjeto(Modelo_obra material) {
        return modeloObraRepositorioPort.guardar(material);
    }
}
