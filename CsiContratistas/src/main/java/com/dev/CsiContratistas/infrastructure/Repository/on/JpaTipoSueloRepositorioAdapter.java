package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoObraEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoSueloEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTipoSueloRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTipoSueloRepositorioAdapter implements ObjectRepositorioPort<Tipo_suelo,Integer> {

    private final IJpaTipoSueloRepositorio iJpaTipoSueloRepositorio;

    @Override
    public Tipo_suelo guardar(Tipo_suelo objeto) {

        TipoSueloEntidad tipoSueloEntidad = TipoSueloEntidad.fromDomainModel(objeto);
        TipoSueloEntidad guardarTipoSueloEntidad = iJpaTipoSueloRepositorio.save(tipoSueloEntidad);

        return guardarTipoSueloEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<TipoSueloEntidad> tipoSueloEntidadOptional = iJpaTipoSueloRepositorio.findById(integer);

        if (!tipoSueloEntidadOptional.isPresent()){
            return 0;
        }

        TipoSueloEntidad rol= tipoSueloEntidadOptional.get();

        rol.setEstado(false);

        TipoSueloEntidad actualizarTipoSueloEntidad=iJpaTipoSueloRepositorio.save(rol);

        return 1;

    }

    @Override
    public Optional<Tipo_suelo> leer(Integer id) {
        return iJpaTipoSueloRepositorio.findById(id).map(TipoSueloEntidad::toDomainModel);
    }

    @Override
    public List<Tipo_suelo> leerObjetos() {
        return iJpaTipoSueloRepositorio.findAll().stream()
                .map(TipoSueloEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tipo_suelo> modificar(Integer id,Tipo_suelo tipoSuelo) {

        Boolean idTipoSuelo = iJpaTipoSueloRepositorio.existsById(id);

        if(!idTipoSuelo){
            return Optional.empty();
        }

        tipoSuelo.setId_tipo_suelo(id);
        TipoSueloEntidad tipoSueloEntidad = TipoSueloEntidad.fromDomainModel(tipoSuelo);
        TipoSueloEntidad actualizarTipoSueloEntidad= iJpaTipoSueloRepositorio.save(tipoSueloEntidad);

        return  Optional.of(actualizarTipoSueloEntidad.toDomainModel());
    }

}