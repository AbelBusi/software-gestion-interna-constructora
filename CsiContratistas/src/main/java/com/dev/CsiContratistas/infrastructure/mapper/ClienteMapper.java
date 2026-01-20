package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.CrearCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.EliminarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.LeerCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.ModificarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.CrearClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.EliminarClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.LeerClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.ModificarClienteDTO;

public class ClienteMapper {

    public static Cliente crearDtoDomain(CrearClienteDTO dto) {

        return new Cliente(
                null,
                dto.getTipo_cliente(),
                dto.getDni(),
                dto.getNombre(),
                dto.getRazon_social(),
                dto.getRuc(),
                dto.getNombre_comercial(),
                dto.getCorreo(),
                dto.getTelefono(),
                dto.getEstado()
        );

    }

    public static LeerClienteDTO leerDTOClienteo(Cliente cliente) {
        return new LeerClienteDTO(
                cliente.getId_cliente(),
                cliente.getTipo_cliente(),
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getRazon_social(),
                cliente.getRuc(),
                cliente.getNombre_comercial(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getEstado()

        );
    }

    public static Cliente actualizarDtoDomain(ModificarClienteDTO clienteDTO) {

        return new Cliente(
                null,
                clienteDTO.getTipo_cliente(),
                clienteDTO.getDni(),
                clienteDTO.getNombre(),
                clienteDTO.getRazon_social(),
                clienteDTO.getRuc(),
                clienteDTO.getNombre_comercial(),
                clienteDTO.getCorreo(),
                clienteDTO.getTelefono(),
                clienteDTO.getEstado()
        );

    }

    public static EliminarClienteDTO eliminarClienteDTODomain(Cliente dto) {
        return new EliminarClienteDTO(
                dto.getId_cliente()
        );
    }

}