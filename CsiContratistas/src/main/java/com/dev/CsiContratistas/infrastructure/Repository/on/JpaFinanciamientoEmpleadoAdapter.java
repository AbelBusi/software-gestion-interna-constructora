package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaFinanciamientoEmpleadolRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaFinanciamientoEmpleadoAdapter implements ObjectRepositorioPort<Financiamiento_empleado,Integer> {

    private final IJpaFinanciamientoEmpleadolRepositorio iJpaFinanciamientoEmpleadolRepositorio;


    @Override
    public Financiamiento_empleado guardar(Financiamiento_empleado objeto) {

        FinanciamientoEmpleadoEntidad financiamientoEmpleadoEntidad = FinanciamientoEmpleadoEntidad.fromDomainModel(objeto);


        FinanciamientoEmpleadoEntidad guardarFinanciamientoEmpleado = iJpaFinanciamientoEmpleadolRepositorio.save(financiamientoEmpleadoEntidad);

        return guardarFinanciamientoEmpleado.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        if (!iJpaFinanciamientoEmpleadolRepositorio.existsById(integer)){
            return 0;
        }

        iJpaFinanciamientoEmpleadolRepositorio.deleteById(integer);

        return 1;

    }

    @Override
    public Optional<Financiamiento_empleado> leer(Integer id) {
        return iJpaFinanciamientoEmpleadolRepositorio.findById(id).map(FinanciamientoEmpleadoEntidad::toDomainModel);
    }

    @Override
    public List<Financiamiento_empleado> leerObjetos() {
        return iJpaFinanciamientoEmpleadolRepositorio.findAll().stream()
                .map(FinanciamientoEmpleadoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Financiamiento_empleado> modificar(Integer id,Financiamiento_empleado financiamientoEmpleado) {

        Boolean idFinanciamientoEmpleado = iJpaFinanciamientoEmpleadolRepositorio.existsById(id);

        if(!idFinanciamientoEmpleado){
            return Optional.empty();
        }

        financiamientoEmpleado.setId_financiamiento_empleado(id);
        FinanciamientoEmpleadoEntidad financiamientoEmpleadoEntidad = FinanciamientoEmpleadoEntidad.fromDomainModel(financiamientoEmpleado);
        FinanciamientoEmpleadoEntidad actualizarFinanciamientoEmpleadoEntidad= iJpaFinanciamientoEmpleadolRepositorio.save(financiamientoEmpleadoEntidad);

        return  Optional.of(actualizarFinanciamientoEmpleadoEntidad.toDomainModel());
    }

}