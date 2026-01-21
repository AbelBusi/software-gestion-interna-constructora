package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.FormaTerrenoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoSueloEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaFormaTerrenoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaFormaTerrenoRepositorioAdapter implements ObjectRepositorioPort<Forma_terreno,Integer> {

    private final IJpaFormaTerrenoRepositorio iJpaFormaTerrenoRepositorio;

    @Override
    public Forma_terreno guardar(Forma_terreno objeto) {

        FormaTerrenoEntidad formaTerrenoEntidad = FormaTerrenoEntidad.fromDomainModel(objeto);
        FormaTerrenoEntidad guardarTerrenoEntidad = iJpaFormaTerrenoRepositorio.save(formaTerrenoEntidad);

        return guardarTerrenoEntidad.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<FormaTerrenoEntidad> formaTerrenoEntidad = iJpaFormaTerrenoRepositorio.findById(integer);

        if (!formaTerrenoEntidad.isPresent()){
            return 0;
        }

        FormaTerrenoEntidad terrenoEntidad= formaTerrenoEntidad.get();

        terrenoEntidad.setEstado(false);

        FormaTerrenoEntidad actualizarFormaTerrenoEntidad=iJpaFormaTerrenoRepositorio.save(terrenoEntidad);

        return 1;

    }

    @Override
    public Optional<Forma_terreno> leer(Integer id) {
        return iJpaFormaTerrenoRepositorio.findById(id).map(FormaTerrenoEntidad::toDomainModel);
    }

    @Override
    public List<Forma_terreno> leerObjetos() {
        return iJpaFormaTerrenoRepositorio.findAll().stream()
                .map(FormaTerrenoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Forma_terreno> modificar(Integer id,Forma_terreno formaTerreno) {

        Boolean idFormaTerreno = iJpaFormaTerrenoRepositorio.existsById(id);

        if(!idFormaTerreno){
            return Optional.empty();
        }

        formaTerreno.setId_forma_terreno(id);
        FormaTerrenoEntidad formaTerrenoEntidad = FormaTerrenoEntidad.fromDomainModel(formaTerreno);
        FormaTerrenoEntidad actualizarFormaTerrenoEntidad= iJpaFormaTerrenoRepositorio.save(formaTerrenoEntidad);

        return  Optional.of(actualizarFormaTerrenoEntidad.toDomainModel());
    }

}