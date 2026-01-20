package com.dev.CsiContratistas.application.CasoUso.Rama;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearRamaCasoUsoImpl implements ICrearCasoUso<Rama> {

    private final ObjectRepositorioPort<Rama,Integer> ramaTipoRepositorioPort;

    @Override
    public Rama crearObjeto(Rama rama) {
        return ramaTipoRepositorioPort.guardar(rama);
    }
}
