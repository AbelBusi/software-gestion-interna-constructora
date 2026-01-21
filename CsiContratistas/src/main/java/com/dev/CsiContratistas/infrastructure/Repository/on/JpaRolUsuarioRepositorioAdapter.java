package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.RamaEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.RolUsuarioEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaRolUsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRolUsuarioRepositorioAdapter implements ObjectRepositorioPort<Rol_usuario,Integer> {


    private final IJpaRolUsuarioRepositorio iJpaRolUsuarioRepositorio;

    @Override
    public Rol_usuario guardar(Rol_usuario objeto) {

        RolUsuarioEntidad rolUsuarioEntidad = RolUsuarioEntidad.fromDomainModel(objeto);
        RolUsuarioEntidad guardarUsuarioEntidad = iJpaRolUsuarioRepositorio.save(rolUsuarioEntidad);

        return guardarUsuarioEntidad.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        if (!iJpaRolUsuarioRepositorio.existsById(integer)){
            return 0;
        }

        iJpaRolUsuarioRepositorio.deleteById(integer);

        return 1;

    }

    @Override
    public Optional<Rol_usuario> leer(Integer id) {
        return iJpaRolUsuarioRepositorio.findById(id).map(RolUsuarioEntidad::toDomainModel);
    }

    @Override
    public List<Rol_usuario> leerObjetos() {
        return iJpaRolUsuarioRepositorio.findAll().stream()
                .map(RolUsuarioEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rol_usuario> modificar(Integer id,Rol_usuario rolUsuario) {

        Boolean idRolUsuario = iJpaRolUsuarioRepositorio.existsById(id);

        if(!idRolUsuario){
            return Optional.empty();
        }

        rolUsuario.setId_rol_usuario(id);
        RolUsuarioEntidad rolUsuarioEntidad = RolUsuarioEntidad.fromDomainModel(rolUsuario);
        RolUsuarioEntidad actualizarRolUsuarioEntidad= iJpaRolUsuarioRepositorio.save(rolUsuarioEntidad);

        return  Optional.of(actualizarRolUsuarioEntidad.toDomainModel());
    }


}
