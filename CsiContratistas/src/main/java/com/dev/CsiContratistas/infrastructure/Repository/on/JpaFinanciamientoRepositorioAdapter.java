package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaFinanciamientoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaFinanciamientoRepositorioAdapter implements ObjectRepositorioPort<Financiamiento,Integer> {

    private final IJpaFinanciamientoRepositorio iJpaFinanciamientoRepositorio;

    @Override
    public Financiamiento guardar(Financiamiento objeto) {

        FinanciamientoEntidad financiamientoEntidad = FinanciamientoEntidad.fromDomainModel(objeto);
        FinanciamientoEntidad guardarFinanciamientoEntidad = iJpaFinanciamientoRepositorio.save(financiamientoEntidad);

        return guardarFinanciamientoEntidad.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        if (!iJpaFinanciamientoRepositorio.existsById(integer)){
            return 0;
        }

        iJpaFinanciamientoRepositorio.deleteById(integer);

        return 1;

    }

    @Override
    public Optional<Financiamiento> leer(Integer id) {
        return iJpaFinanciamientoRepositorio.findById(id).map(FinanciamientoEntidad::toDomainModel);
    }

    @Override
    public List<Financiamiento> leerObjetos() {
        return iJpaFinanciamientoRepositorio.findAll().stream()
                .map(FinanciamientoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Financiamiento> modificar(Integer id,Financiamiento financiamiento) {

        Boolean idFinanciamiento = iJpaFinanciamientoRepositorio.existsById(id);

        if(!idFinanciamiento){
            return Optional.empty();
        }

        financiamiento.setId_financiamiento(id);
        FinanciamientoEntidad financiamientoEntidad = FinanciamientoEntidad.fromDomainModel(financiamiento);
        FinanciamientoEntidad actualizarFinanciamientoEntidad= iJpaFinanciamientoRepositorio.save(financiamientoEntidad);

        return  Optional.of(actualizarFinanciamientoEntidad.toDomainModel());
    }

}