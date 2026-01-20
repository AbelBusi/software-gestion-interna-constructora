package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.PermisoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaPermisoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaPermisoRepositorioAdapter implements ObjectRepositorioPort<Permiso,Integer> {

    private final IJpaPermisoRepositorio jpaPermisoRepositorio;

    @Override
    public Permiso guardar(Permiso objeto) {

        PermisoEntidad permisoEntidad = PermisoEntidad.fromDomainModel(objeto);
        PermisoEntidad guardarPermisoEntidad = jpaPermisoRepositorio.save(permisoEntidad);

        return guardarPermisoEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<PermisoEntidad> clienteEntidadOptional = jpaPermisoRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        PermisoEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        PermisoEntidad actualizarClienteEntidad=jpaPermisoRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override
    public Optional<Permiso> leer(Integer id) {
        return jpaPermisoRepositorio.findById(id).map(PermisoEntidad::toDomainModel);
    }

    @Override
    public List<Permiso> leerObjetos() {
        return jpaPermisoRepositorio.findAll().stream()
                .map(PermisoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Permiso> modificar(Integer id,Permiso permiso) {

        Boolean idRol = jpaPermisoRepositorio.existsById(id);

        if(!idRol){
            return Optional.empty();
        }

        permiso.setId_permiso(id);
        PermisoEntidad permisoEntidad = PermisoEntidad.fromDomainModel(permiso);
        PermisoEntidad actualizarPermisoEntidad=jpaPermisoRepositorio.save(permisoEntidad);

        return  Optional.of(actualizarPermisoEntidad.toDomainModel());
    }


}
