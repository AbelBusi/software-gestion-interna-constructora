package com.dev.CsiContratistas.application.CasoUso.Cargos;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CrearCargoCasoUsoImpl implements ICrearCasoUso<Cargo> {

    private final ObjectRepositorioPort<Cargo,Integer> cargoRepositorioPort;

    @Override
    public Cargo crearObjeto(Cargo cargo) {
        return cargoRepositorioPort.guardar(cargo);
    }
}
