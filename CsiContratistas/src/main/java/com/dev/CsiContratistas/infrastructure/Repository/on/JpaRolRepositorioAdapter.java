package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoMaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaRolRepositorio;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTipoMaterialRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRolRepositorioAdapter implements ObjectRepositorioPort<Rol,Integer> {

    private final IJpaRolRepositorio jpaRolRepositorio;

    @Override
    public Rol guardar(Rol objeto) {

        RolEntidad rolEntidad = RolEntidad.fromDomainModel(objeto);
        RolEntidad guardarRolEntidad = jpaRolRepositorio.save(rolEntidad);

        return guardarRolEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<RolEntidad> rolEntidadOptional = jpaRolRepositorio.findById(integer);

        if (!rolEntidadOptional.isPresent()){
            return 0;
        }

        RolEntidad rol= rolEntidadOptional.get();

        rol.setEstado(false);

        RolEntidad actualizarRolEntidad=jpaRolRepositorio.save(rol);

        return 1;

    }

    @Override
    public Optional<Rol> leer(Integer id) {
        return jpaRolRepositorio.findById(id).map(RolEntidad::toDomainModel);
    }

    @Override
    public List<Rol> leerObjetos() {
        return jpaRolRepositorio.findAll().stream()
                .map(RolEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rol> modificar(Integer id,Rol rol) {

        Boolean idRol = jpaRolRepositorio.existsById(id);

        if(!idRol){
            return Optional.empty();
        }

        rol.setId_rol(id);
        RolEntidad rolEntidad = RolEntidad.fromDomainModel(rol);
        RolEntidad actualizarRolEntidad=jpaRolRepositorio.save(rolEntidad);

        return  Optional.of(actualizarRolEntidad.toDomainModel());
    }
    public Optional<Rol> buscarpornombre(String nombre) {
        Optional <RolEntidad> rolEntidad = jpaRolRepositorio.findByNombre(nombre);
        if(rolEntidad.isPresent()){
            RolEntidad rolEntidadActual = rolEntidad.get();
            return Optional.of(rolEntidadActual.toDomainModel());
        } else {
            return Optional.empty();
        }
    }

}
