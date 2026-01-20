package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.*;

public class EmpleadoMapper {
        public static Empleado crearDtoDomain(CrearEmpleadoDTO dto) {

        return new Empleado(
                null,
                dto.getDni(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getFecha_nacimiento(),
                dto.getGenero(),
                dto.getTelefono(),
                dto.getEstado_civil(),
                dto.getDireccion(),
                dto.getEstado()
        );
    }

    public static LeerEmpleadoDTO leerDTOEmpleado(Empleado empleado) {
        return new LeerEmpleadoDTO(
                empleado.getId_empleado(),
                empleado.getDni(),
                empleado.getNombre(),
                empleado.getApellidos(),
                empleado.getFecha_nacimiento(),
                empleado.getGenero(),
                empleado.getTelefono(),
                empleado.getEstado_civil(),
                empleado.getDireccion(),
                empleado.getEstado()
        );
    }

    public static VerificarDniExistenteDTO VerificarDniEmpleadoDTO(String dto){

            return new VerificarDniExistenteDTO(
                    dto
            );

    }

    public static Empleado actualizarDtoDomain(ModificarEmpleadoDTO dto) {

        return new Empleado(
                null,
                dto.getDni(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getFecha_nacimiento(),
                dto.getGenero(),
                dto.getTelefono(),
                dto.getEstado_civil(),
                dto.getDireccion(),
                dto.getEstado()
        );
    }

    public static EliminarEmpleadoDTO eliminarEmpleadoDTODomain(Empleado dto) {
        return new EliminarEmpleadoDTO(
                dto.getId_empleado()
        );
    }
}