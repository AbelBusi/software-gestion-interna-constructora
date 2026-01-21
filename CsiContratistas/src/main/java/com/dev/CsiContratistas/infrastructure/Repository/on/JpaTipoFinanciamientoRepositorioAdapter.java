package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.FormaTerrenoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoFinanciamientoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaTipoFinanciamientoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTipoFinanciamientoRepositorioAdapter implements ObjectRepositorioPort<Tipo_financiamiento,Integer> {

    private final IJpaTipoFinanciamientoRepositorio iJpaTipoFinanciamientoRepositorio;

    @Override
    public Tipo_financiamiento guardar(Tipo_financiamiento objeto) {

        TipoFinanciamientoEntidad tipoFinanciamientoEntidad = TipoFinanciamientoEntidad.fromDomainModel(objeto);
        TipoFinanciamientoEntidad guardarTipoFinanciamientoEntidad = iJpaTipoFinanciamientoRepositorio.save(tipoFinanciamientoEntidad);

        return guardarTipoFinanciamientoEntidad.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<TipoFinanciamientoEntidad> clienteEntidadOptional = iJpaTipoFinanciamientoRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        TipoFinanciamientoEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        TipoFinanciamientoEntidad actualizarClienteEntidad=iJpaTipoFinanciamientoRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override
    public Optional<Tipo_financiamiento> leer(Integer id) {
        return iJpaTipoFinanciamientoRepositorio.findById(id).map(TipoFinanciamientoEntidad::toDomainModel);
    }

    @Override
    public List<Tipo_financiamiento> leerObjetos() {
        return iJpaTipoFinanciamientoRepositorio.findAll().stream()
                .map(TipoFinanciamientoEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tipo_financiamiento> modificar(Integer id,Tipo_financiamiento tipoFinanciamiento) {

        Boolean idTipoFinanciamiento = iJpaTipoFinanciamientoRepositorio.existsById(id);

        if(!idTipoFinanciamiento){

            return Optional.empty();

        }

        tipoFinanciamiento.setId_tipo_financiamiento(id);
        TipoFinanciamientoEntidad tipoFinanciamientoEntidad = TipoFinanciamientoEntidad.fromDomainModel(tipoFinanciamiento);
        TipoFinanciamientoEntidad actualizarFormaTipoFinanciamientoEntidad= iJpaTipoFinanciamientoRepositorio.save(tipoFinanciamientoEntidad);

        return  Optional.of(actualizarFormaTipoFinanciamientoEntidad.toDomainModel());

    }

}