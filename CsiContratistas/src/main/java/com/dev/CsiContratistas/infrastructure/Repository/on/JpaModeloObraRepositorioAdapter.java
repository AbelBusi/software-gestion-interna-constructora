package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ModeloObraEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ModeloObraEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaModeloObraRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaModeloObraRepositorioAdapter implements ObjectRepositorioPort<Modelo_obra,Integer> {
        private final IJpaModeloObraRepositorio iJpaModeloObraRepositorio;

    @Override
    public Modelo_obra guardar(Modelo_obra objeto) {

        ModeloObraEntidad modeloObraEntidad = ModeloObraEntidad.fromDomainModel(objeto);
        ModeloObraEntidad guardarModeloObraEntidad = iJpaModeloObraRepositorio.save(modeloObraEntidad);

        return guardarModeloObraEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<ModeloObraEntidad> clienteEntidadOptional = iJpaModeloObraRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        ModeloObraEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        ModeloObraEntidad actualizarClienteEntidad=iJpaModeloObraRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override
    public Optional<Modelo_obra> leer(Integer id) {
        return iJpaModeloObraRepositorio.findById(id).map(ModeloObraEntidad::toDomainModel);
    }

    @Override
    public List<Modelo_obra> leerObjetos() {
        return iJpaModeloObraRepositorio.findAll().stream()
                .map(ModeloObraEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Modelo_obra> modificar(Integer id,Modelo_obra modeloObra) {

        Boolean idModeloObra = iJpaModeloObraRepositorio.existsById(id);

        if(!idModeloObra){
            return Optional.empty();
        }

        modeloObra.setId_modelo_obra(id);
        ModeloObraEntidad modeloObraEntidad = ModeloObraEntidad.fromDomainModel(modeloObra);
        ModeloObraEntidad actualizarModeloObraEntidad= iJpaModeloObraRepositorio.save(modeloObraEntidad);

        return  Optional.of(actualizarModeloObraEntidad.toDomainModel());
    }


}
