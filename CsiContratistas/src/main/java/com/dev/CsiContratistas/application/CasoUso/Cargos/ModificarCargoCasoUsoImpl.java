package com.dev.CsiContratistas.application.CasoUso.Cargos;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ModificarCargoCasoUsoImpl implements IModificarCasoUso<Cargo> {

    private final ObjectRepositorioPort<Cargo,Integer> cargoRepositorioPort;

    @Override
    public Optional<Cargo> modificarObjeto(Integer id,Cargo cargo) {
        return cargoRepositorioPort.modificar(id,cargo);
    }

}