package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.ports.in.ICrearCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IEliminarCasoUso;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.in.IModificarCasoUso;
import com.dev.CsiContratistas.infrastructure.Controller.CargoControlador;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class CargoServicio implements ICrearCasoUso<Cargo>, ILeerCasoUso<Cargo>, IModificarCasoUso<Cargo>, IEliminarCasoUso<Integer> {
    private final ILeerCasoUso<Cargo> leerCargo;

    private final ICrearCasoUso<Cargo> crearCargo;

    private final IModificarCasoUso<Cargo> modificarCargo;

    private final IEliminarCasoUso<Integer> eliminarCargoPorId;

    private static final Logger logger = LoggerFactory.getLogger(CargoServicio.class);


    @Override
    public Cargo crearObjeto(Cargo cargo) {
        logger.info("Id {}, {}, {}",cargo.getId_profesion().getId_profesion(),cargo.getId_rama().getId_rama(),cargo.getId_empleado().getId_empleado());
        return crearCargo.crearObjeto(cargo);
    }

    @Override
    public Integer eliminarObjeto(Integer idRama) {
        return eliminarCargoPorId.eliminarObjeto(idRama);
    }

    @Override
    public Optional<Cargo> leerObjeto(Integer id) {
        return leerCargo.leerObjeto(id);
    }

    @Override
    public List<Cargo> leerObjetos() {
        return leerCargo.leerObjetos();
    }

    @Override
    public Optional<Cargo> modificarObjeto(Integer id,Cargo rama) {
        return modificarCargo.modificarObjeto(id,rama);
    }
}