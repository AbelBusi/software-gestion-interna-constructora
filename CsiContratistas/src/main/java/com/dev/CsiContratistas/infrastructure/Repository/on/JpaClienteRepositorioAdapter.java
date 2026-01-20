package com.dev.CsiContratistas.infrastructure.Repository.on;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.EmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaClienteRepositorioAdapter implements ObjectRepositorioPort<Cliente,Integer> {

    private final IJpaClienteRepositorio iJpaClienteRepositorio;

    @Override
    public Cliente guardar(Cliente objeto) {

        ClienteEntidad clienteEntidad = ClienteEntidad.fromDomainModel(objeto);
        ClienteEntidad guardarClienteEntidad = iJpaClienteRepositorio.save(clienteEntidad);

        return guardarClienteEntidad.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<ClienteEntidad> clienteEntidadOptional = iJpaClienteRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        ClienteEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        ClienteEntidad actualizarClienteEntidad=iJpaClienteRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override
    public Optional<Cliente> leer(Integer id) {
        return iJpaClienteRepositorio.findById(id).map(ClienteEntidad::toDomainModel);
    }

    @Override
    public List<Cliente> leerObjetos() {
        return iJpaClienteRepositorio.findAll().stream()
                .map(ClienteEntidad::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> modificar(Integer id,Cliente cliente) {

        Boolean idCliente = iJpaClienteRepositorio.existsById(id);

        if(!idCliente){
            return Optional.empty();
        }

        cliente.setId_cliente(id);
        ClienteEntidad ramaEntidad = ClienteEntidad.fromDomainModel(cliente);
        ClienteEntidad actualizarRamaEntidad= iJpaClienteRepositorio.save(ramaEntidad);

        return  Optional.of(actualizarRamaEntidad.toDomainModel());
    }

}