package com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearTipoFinanciamientoCasoUsoImpl implements ICrearCasoUso<Tipo_financiamiento> {

    private final ObjectRepositorioPort<Tipo_financiamiento,Integer> TipoFinanciamientoRepositorioPort;

    @Override
    public Tipo_financiamiento crearObjeto(Tipo_financiamiento tipoFinanciamiento) {
        return TipoFinanciamientoRepositorioPort.guardar(tipoFinanciamiento);
    }
}
