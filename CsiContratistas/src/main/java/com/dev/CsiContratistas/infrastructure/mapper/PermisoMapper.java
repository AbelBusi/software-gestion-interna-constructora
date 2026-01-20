package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.CrearPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.LeerPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.ModificarPermisoDTO;

public class PermisoMapper {
        public static Permiso crearDtoDomain(CrearPermisoDTO dto) {

        return new Permiso(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerPermisoDTO leerDTOPermiso(Permiso permiso) {
        return new LeerPermisoDTO(
                permiso.getId_permiso(),
                permiso.getNombre(),
                permiso.getDescripcion(),
                permiso.getFecha_asignacion(),
                permiso.getEstado()
        );
    }

    public static Permiso actualizarDtoDomain(ModificarPermisoDTO dto) {

        return new Permiso(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarPermisoDTO eliminarPermisoDTODomain(Permiso dto) {
        return new EliminarPermisoDTO(
                dto.getId_permiso()
        );
    }

}
