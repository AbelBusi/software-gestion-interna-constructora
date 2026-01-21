package com.dev.CsiContratistas.application.CasoUso.TipoConstruccion;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearTipoConstruccionCasoUsoImpl implements ICrearCasoUso<Tipo_construccion> {

    private final ObjectRepositorioPort<Tipo_construccion,Integer> tipoConstruccionRepositorioPort;

    @Override
    public Tipo_construccion crearObjeto(Tipo_construccion tipoConstruccion) {
        return tipoConstruccionRepositorioPort.guardar(tipoConstruccion);
    }
}
