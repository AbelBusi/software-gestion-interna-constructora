package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoMaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaFinanciamientoMaterialRepositorio;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaFinanciamientoMaterialAdapter implements ObjectRepositorioPort<Financiamiento_material,Integer> {

    private final IJpaFinanciamientoMaterialRepositorio iJpaFinanciamientoMaterialRepositorio;


    @Override
    public Financiamiento_material guardar(Financiamiento_material objeto) {

        FinanciamientoMaterialEntidad financiamientoEntidad = FinanciamientoMaterialEntidad.fromDomainModel(objeto);


        FinanciamientoMaterialEntidad guardarFinanciamiento = iJpaFinanciamientoMaterialRepositorio.save(financiamientoEntidad);

        return guardarFinanciamiento.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        if (!iJpaFinanciamientoMaterialRepositorio.existsById(integer)){
            return 0;
        }

        iJpaFinanciamientoMaterialRepositorio.deleteById(integer);

        return 1;

    }

    @Override
    public Optional<Financiamiento_material> leer(Integer id) {
        return iJpaFinanciamientoMaterialRepositorio.findById(id).map(FinanciamientoMaterialEntidad::toDomainModel);
    }

    @Override
    public List<Financiamiento_material> leerObjetos() {
        return iJpaFinanciamientoMaterialRepositorio.findAll().stream()
                .map(FinanciamientoMaterialEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Financiamiento_material> modificar(Integer id,Financiamiento_material financiamientoMaterial) {

        Boolean idFinanciamientoMaterial = iJpaFinanciamientoMaterialRepositorio.existsById(id);

        if(!idFinanciamientoMaterial){
            return Optional.empty();
        }

        financiamientoMaterial.setId_financiamiento_material(id);
        FinanciamientoMaterialEntidad financiamientoMaterialEntidad = FinanciamientoMaterialEntidad.fromDomainModel(financiamientoMaterial);
        FinanciamientoMaterialEntidad actualizarFinanciamientoMaterialEntidad= iJpaFinanciamientoMaterialRepositorio.save(financiamientoMaterialEntidad);

        return  Optional.of(actualizarFinanciamientoMaterialEntidad.toDomainModel());
    }

}