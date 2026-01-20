package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Obra;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ObraEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaObraRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaObraRepositorioAdapter implements ObjectRepositorioPort<Obra,Integer> {
        private final IJpaObraRepositorio iJpaObraRepositorio;

    @Override
    public Obra guardar(Obra objeto) {

        ObraEntidad obraEntidad = ObraEntidad.fromDomainModel(objeto);
        ObraEntidad guardarObraEntidad = iJpaObraRepositorio.save(obraEntidad);

        return guardarObraEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<ObraEntidad> clienteEntidadOptional = iJpaObraRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        ObraEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        ObraEntidad actualizarClienteEntidad= iJpaObraRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override
    public Optional<Obra> leer(Integer id) {
        return iJpaObraRepositorio.findById(id).map(ObraEntidad::toDomainModel);
    }

    @Override
    public List<Obra> leerObjetos() {
        return iJpaObraRepositorio.findAll().stream()
                .map(ObraEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Obra> modificar(Integer id,Obra modeloObra) {

        Boolean idModeloObra = iJpaObraRepositorio.existsById(id);

        if(!idModeloObra){
            return Optional.empty();
        }

        modeloObra.setId_obra(id);
        ObraEntidad modeloObraEntidad = ObraEntidad.fromDomainModel(modeloObra);
        ObraEntidad actualizarModeloObraEntidad= iJpaObraRepositorio.save(modeloObraEntidad);

        return  Optional.of(actualizarModeloObraEntidad.toDomainModel());
    }


}
