package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.CrearProfesionDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.EliminarProfesionDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.LeerProfesionDTO;
import com.dev.CsiContratistas.infrastructure.dto.Profesion.ModificarProfesionDTO;

public class ProfesionMapper {

    public static Profesiones crearDtoDomain(CrearProfesionDTO dto) {

        return new Profesiones(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerProfesionDTO leerDTOProfesion(Profesiones profesiones) {
        return new LeerProfesionDTO(
                profesiones.getId_profesion(),
                profesiones.getNombre(),
                profesiones.getDescripcion(),
                profesiones.getFecha_asignacion(),
                profesiones.getEstado()
        );
    }

    public static Profesiones actualizarDtoDomain(ModificarProfesionDTO dto) {

        return new Profesiones(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarProfesionDTO eliminarProfesionDTODomain(Profesiones dto) {
        return new EliminarProfesionDTO(
                dto.getId_profesion()
        );
    }

}
