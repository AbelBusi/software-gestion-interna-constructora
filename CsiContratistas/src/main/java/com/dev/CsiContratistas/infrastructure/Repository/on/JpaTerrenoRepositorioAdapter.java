package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.EmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TerrenoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTerrenoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTerrenoRepositorioAdapter implements ObjectRepositorioPort<Terreno,Integer> {

    private final IJpaTerrenoRepositorio iJpaTerrenoRepositorio;

    @Override
    public Terreno guardar(Terreno objeto) {

        TerrenoEntidad terrenoEntidad = TerrenoEntidad.fromDomainModel(objeto);
        TerrenoEntidad guardarTerrenoEntidad = iJpaTerrenoRepositorio.save(terrenoEntidad);

        return guardarTerrenoEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<TerrenoEntidad> terrenoEntidadOptional = iJpaTerrenoRepositorio.findById(integer);

        if (!terrenoEntidadOptional.isPresent()){
            return 0;
        }

        TerrenoEntidad terreno= terrenoEntidadOptional.get();

        terreno.setEstado(false);

        TerrenoEntidad actualizarTerrenoEntidad=iJpaTerrenoRepositorio.save(terreno);

        return 1;

    }

    @Override
    public Optional<Terreno> leer(Integer id) {
        return iJpaTerrenoRepositorio.findById(id).map(TerrenoEntidad::toDomainModel);
    }

    @Override
    public List<Terreno> leerObjetos() {
        return iJpaTerrenoRepositorio.findAll().stream()
                .map(TerrenoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Terreno> modificar(Integer id,Terreno terreno) {

        Boolean idTerreno = iJpaTerrenoRepositorio.existsById(id);

        if(!idTerreno){
            return Optional.empty();
        }

        terreno.setId_terreno(id);
        TerrenoEntidad terrenoEntidad = TerrenoEntidad.fromDomainModel(terreno);
        TerrenoEntidad actualizarTerrenoEntidad= iJpaTerrenoRepositorio.save(terrenoEntidad);

        return  Optional.of(actualizarTerrenoEntidad.toDomainModel());
    }

}
