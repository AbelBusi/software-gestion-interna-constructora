package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Controller.CargoControlador;
import com.dev.CsiContratistas.infrastructure.Entity.CargoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaCargoRepositorio;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaCargoRepositorioAdapter implements ObjectRepositorioPort<Cargo,Integer> {

    private final IJpaCargoRepositorio jpaCargoRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(JpaCargoRepositorioAdapter.class);


    @Override
    public Cargo guardar(Cargo objeto) {


        CargoEntidad cargoEntidad = CargoEntidad.fromDomainModel(objeto);


        CargoEntidad guardarCargo = jpaCargoRepositorio.save(cargoEntidad);

        return guardarCargo.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        if (!jpaCargoRepositorio.existsById(integer)){
            return 0;
        }

        jpaCargoRepositorio.deleteById(integer);

        return 1;

    }

    @Override
    public Optional<Cargo> leer(Integer id) {
        return jpaCargoRepositorio.findById(id).map(CargoEntidad::toDomainModel);
    }

    @Override
    public List<Cargo> leerObjetos() {
        return jpaCargoRepositorio.findAll().stream()
                .map(CargoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cargo> modificar(Integer id,Cargo cargo) {

        Boolean idCargo = jpaCargoRepositorio.existsById(id);

        if(!idCargo){
            return Optional.empty();
        }

        cargo.setId_cargo(id);
        CargoEntidad cargoEntidad = CargoEntidad.fromDomainModel(cargo);
        CargoEntidad actualizarCargoEntidad= jpaCargoRepositorio.save(cargoEntidad);

        return  Optional.of(actualizarCargoEntidad.toDomainModel());
    }

}