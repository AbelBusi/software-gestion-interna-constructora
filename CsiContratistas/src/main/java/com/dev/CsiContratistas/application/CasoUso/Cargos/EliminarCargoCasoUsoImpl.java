package com.dev.CsiContratistas.application.CasoUso.Cargos;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarCargoCasoUsoImpl implements IEliminarCasoUso<Integer> {

    private final ObjectRepositorioPort<Cargo,Integer> cargoRepositorioPort;

    @Override
    public Integer eliminarObjeto(Integer idCargo) {
        return cargoRepositorioPort.eliminar(idCargo);
    }
}