package com.dev.CsiContratistas.application.CasoUso.Cargos;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerCargoCasoUsoImpl implements ILeerCasoUso<Cargo> {

    private final ObjectRepositorioPort<Cargo,Integer> cargoObjectRepositorioPort;


    @Override
    public Optional<Cargo> leerObjeto(Integer id) {
        return cargoObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Cargo> leerObjetos() {
        return cargoObjectRepositorioPort.leerObjetos();
    }
}