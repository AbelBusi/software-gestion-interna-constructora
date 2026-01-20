package com.dev.CsiContratistas.application.CasoUso.TipoSuelo;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearTipoSueloCasoUsoImpl implements ICrearCasoUso<Tipo_suelo> {

    private final ObjectRepositorioPort<Tipo_suelo,Integer> tipoSueloRepositorioPort;

    @Override
    public Tipo_suelo crearObjeto(Tipo_suelo tipoObra) {
        return tipoSueloRepositorioPort.guardar(tipoObra);
    }
}
