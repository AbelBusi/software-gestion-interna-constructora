package com.dev.CsiContratistas.application.CasoUso.TipoObra;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearTipoObraCasoUsoImpl implements ICrearCasoUso<Tipo_obra> {

    private final ObjectRepositorioPort<Tipo_obra,Integer> tipoObraRepositorioPort;

    @Override
    public Tipo_obra crearObjeto(Tipo_obra tipoObra) {
        return tipoObraRepositorioPort.guardar(tipoObra);
    }
}
