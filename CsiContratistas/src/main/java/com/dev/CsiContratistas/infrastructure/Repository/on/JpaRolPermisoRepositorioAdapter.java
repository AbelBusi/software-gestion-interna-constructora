package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Rol_Permiso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.RamaEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolPermisoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaRolPermisoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRolPermisoRepositorioAdapter implements ObjectRepositorioPort<Rol_Permiso,Integer> {

    private final IJpaRolPermisoRepositorio jpaRolPermisoRepositorio;

    @Override
    public Rol_Permiso guardar(Rol_Permiso objeto) {

        RolPermisoEntidad rolPermiso = RolPermisoEntidad.fromDomainModel(objeto);
        RolPermisoEntidad guardarRolPermisoEntidad = jpaRolPermisoRepositorio.save(rolPermiso);

        return guardarRolPermisoEntidad.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        if (!jpaRolPermisoRepositorio.existsById(integer)){
            return 0;
        }

        jpaRolPermisoRepositorio.deleteById(integer);

        return 1;

    }

    @Override
    public Optional<Rol_Permiso> leer(Integer id) {
        return jpaRolPermisoRepositorio.findById(id).map(RolPermisoEntidad::toDomainModel);
    }

    @Override
    public List<Rol_Permiso> leerObjetos() {
        return jpaRolPermisoRepositorio.findAll().stream()
                .map(RolPermisoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rol_Permiso> modificar(Integer id,Rol_Permiso rol_permiso) {

        Boolean idRolPermiso = jpaRolPermisoRepositorio.existsById(id);

        if(!idRolPermiso){
            return Optional.empty();
        }

        rol_permiso.setId_rol_Permiso(id);
        RolPermisoEntidad rolPermisoEntidad = RolPermisoEntidad.fromDomainModel(rol_permiso);
        RolPermisoEntidad actualizarRolPermisoEntidad= jpaRolPermisoRepositorio.save(rolPermisoEntidad);

        return  Optional.of(actualizarRolPermisoEntidad.toDomainModel());
    }


}