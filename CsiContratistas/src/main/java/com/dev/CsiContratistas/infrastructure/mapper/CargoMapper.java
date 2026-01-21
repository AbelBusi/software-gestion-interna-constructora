package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.CrearCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.EliminarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.LeerCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.ModificarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.CrearEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.EliminarEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.LeerEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.ModificarEmpleadoDTO;

public class CargoMapper {

    public static Cargo crearDtoDomain(CrearCargoDTO dto, Empleado empleado, Rama rama, Profesiones profesiones) {

        return new Cargo(
                null,
                empleado,
                rama,
                profesiones,
                dto.getFecha_asignacion(),
                dto.getEstado()
        );

    }

    public static LeerCargoDTO leerDTOCargo(Cargo cargo) {
        return new LeerCargoDTO(

                cargo.getId_cargo(),
                cargo.getId_empleado().getId_empleado(),
                cargo.getId_rama().getId_rama(),
                cargo.getId_profesion().getId_profesion(),
                cargo.getFecha_asignacion(),
                cargo.getEstado()

        );
    }

    public static Cargo actualizarDtoDomain(ModificarCargoDTO dtoEmpleado, Empleado empleado, Rama rama, Profesiones profesiones) {

        return new Cargo(
                null,
                empleado,
                rama,
                profesiones,
                dtoEmpleado.getFecha_asignacion(),
                dtoEmpleado.getEstado()
        );

    }

    public static EliminarCargoDTO eliminarCargoDTODomain(Cargo dto) {
        return new EliminarCargoDTO(
                dto.getId_cargo()
        );
    }

}