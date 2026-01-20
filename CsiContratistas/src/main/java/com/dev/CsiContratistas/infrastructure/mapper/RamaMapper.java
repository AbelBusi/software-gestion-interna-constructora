package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.CrearPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.LeerPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.ModificarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.EliminarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;

public class RamaMapper {
        public static Rama crearDtoDomain(CrearRamaDTO dto,Profesiones profesiones) {

        return new Rama(
                null,
                profesiones,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerRamaDTO leerDTORama(Rama rama) {
        return new LeerRamaDTO(
                rama.getId_rama(),
                rama.getId_profesion().getId_profesion(),
                rama.getNombre(),
                rama.getDescripcion(),
                rama.getFecha_asignacion(),
                rama.getEstado()
        );
    }

    public static Rama actualizarDtoDomain(ModificarRamaDTO dto,Profesiones profesiones) {

        return new Rama(
                null,
                profesiones,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarRamaDTO eliminarPermisoDTODomain(Rama dto) {
        return new EliminarRamaDTO(
                dto.getId_rama()
        );
    }

}
