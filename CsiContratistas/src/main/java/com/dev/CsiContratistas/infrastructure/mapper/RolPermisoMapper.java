package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Rama.EliminarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.CrearRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.EliminarRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.LeerRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolPermiso.ModificarRolPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.CrearRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.LeerRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.ModificarRolUsuarioDTO;

public class RolPermisoMapper {
        public static Rol_Permiso crearDtoDomain(CrearRolPermisoDTO dto, Permiso permiso, Rol rol) {

        return new Rol_Permiso(
                null,
                rol,
                permiso,
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerRolPermisoDTO leerDTORolPermiso(Rol_Permiso rolPermiso) {
        return new LeerRolPermisoDTO(
                rolPermiso.getId_rol_Permiso(),
                rolPermiso.getId_rol().getId_rol(),
                rolPermiso.getId_permiso().getId_permiso(),
                rolPermiso.getFecha_asignacion(),
                rolPermiso.getEstado()
        );
    }

    public static Rol_Permiso actualizarDtoDomain(ModificarRolPermisoDTO dto, Permiso permiso, Rol rol) {

        return new Rol_Permiso(
                null,
                rol,
                permiso,
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarRolPermisoDTO eliminarRolUsuarioDTODomain(Rol_Permiso dto) {
        return new EliminarRolPermisoDTO(
                dto.getId_rol_Permiso()
        );
    }

}
