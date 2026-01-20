package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoMaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoObraEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTipoObraRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTipoObraRepositorioAdapter implements ObjectRepositorioPort<Tipo_obra,Integer> {

    private final IJpaTipoObraRepositorio iJpaTipoObraRepositorio;

    @Override
    public Tipo_obra guardar(Tipo_obra objeto) {

        TipoObraEntidad tipoObraEntidad = TipoObraEntidad.fromDomainModel(objeto);
        TipoObraEntidad guardarTipoObraEntidad = iJpaTipoObraRepositorio.save(tipoObraEntidad);

        return guardarTipoObraEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<TipoObraEntidad> tipoObraOptional = iJpaTipoObraRepositorio.findById(integer);

        if (!tipoObraOptional.isPresent()){
            return 0;
        }

        TipoObraEntidad tipoObraEntidad= tipoObraOptional.get();

        tipoObraEntidad.setEstado(false);

            TipoObraEntidad actualizarRolEntidad=iJpaTipoObraRepositorio.save(tipoObraEntidad);

        return 1;

    }

    @Override
    public Optional<Tipo_obra> leer(Integer id) {
        return iJpaTipoObraRepositorio.findById(id).map(TipoObraEntidad::toDomainModel);
    }

    @Override
    public List<Tipo_obra> leerObjetos() {
        return iJpaTipoObraRepositorio.findAll().stream()
                .map(TipoObraEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tipo_obra> modificar(Integer id,Tipo_obra tipoObra) {

        Boolean idTipoObra = iJpaTipoObraRepositorio.existsById(id);

        if(!idTipoObra){
            return Optional.empty();
        }

        tipoObra.setId_tipo_obra(id);
        TipoObraEntidad tipoObraEntidad = TipoObraEntidad.fromDomainModel(tipoObra);
        TipoObraEntidad actualizarTipoObraEntidad= iJpaTipoObraRepositorio.save(tipoObraEntidad);

        return  Optional.of(actualizarTipoObraEntidad.toDomainModel());
    }

}
