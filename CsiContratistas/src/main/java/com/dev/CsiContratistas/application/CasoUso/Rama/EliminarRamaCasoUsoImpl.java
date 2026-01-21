package com.dev.CsiContratistas.application.CasoUso.Rama;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarRamaCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Rama,Integer> ramaRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idRamal) {
        return ramaRepositorioPort.eliminar(idRamal);
    }
}