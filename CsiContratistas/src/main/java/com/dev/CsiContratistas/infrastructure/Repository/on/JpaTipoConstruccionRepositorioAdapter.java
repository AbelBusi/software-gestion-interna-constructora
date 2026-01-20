package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TerrenoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoConstruccionEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTipoConstruccionRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTipoConstruccionRepositorioAdapter implements ObjectRepositorioPort<Tipo_construccion,Integer> {

    private final IJpaTipoConstruccionRepositorio iJpaTipoConstruccionRepositorio;

    @Override
    public Tipo_construccion guardar(Tipo_construccion objeto) {

        TipoConstruccionEntidad tipoConstruccionEntidad = TipoConstruccionEntidad.fromDomainModel(objeto);
        TipoConstruccionEntidad guardarTipoConstruccionEntidad = iJpaTipoConstruccionRepositorio.save(tipoConstruccionEntidad);

        return guardarTipoConstruccionEntidad.toDomainModel();
    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<TipoConstruccionEntidad> clienteEntidadOptional = iJpaTipoConstruccionRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        TipoConstruccionEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        TipoConstruccionEntidad actualizarClienteEntidad=iJpaTipoConstruccionRepositorio.save(clienteEntidad);

        return 1;
    }

    @Override
    public Optional<Tipo_construccion> leer(Integer id) {
        return iJpaTipoConstruccionRepositorio.findById(id).map(TipoConstruccionEntidad::toDomainModel);
    }

    @Override
    public List<Tipo_construccion> leerObjetos() {
        return iJpaTipoConstruccionRepositorio.findAll().stream()
                .map(TipoConstruccionEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tipo_construccion> modificar(Integer id,Tipo_construccion tipoConstruccion) {

        Boolean idTipoConstruccion = iJpaTipoConstruccionRepositorio.existsById(id);

        if(!idTipoConstruccion){
            return Optional.empty();
        }

        tipoConstruccion.setId_tipo_construccion(id);
        TipoConstruccionEntidad tipoConstruccionEntidad = TipoConstruccionEntidad.fromDomainModel(tipoConstruccion);
        TipoConstruccionEntidad actualizarTipoConstruccionEntidad= iJpaTipoConstruccionRepositorio.save(tipoConstruccionEntidad);

        return  Optional.of(actualizarTipoConstruccionEntidad.toDomainModel());
    }

}
