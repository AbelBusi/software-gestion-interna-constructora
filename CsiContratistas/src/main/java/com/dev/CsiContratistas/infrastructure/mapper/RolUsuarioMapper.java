package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.EliminarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.CrearRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.LeerRolUsuarioDTO;
import com.dev.CsiContratistas.infrastructure.dto.RolUsuario.ModificarRolUsuarioDTO;

public class RolUsuarioMapper {
        public static Rol_usuario crearDtoDomain(CrearRolUsuarioDTO dto, Usuario usuario, Rol rol) {

        return new Rol_usuario(
                null,
                rol,
                usuario,
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerRolUsuarioDTO leerDTORolUsuario(Rol_usuario rolUsuario) {
        return new LeerRolUsuarioDTO(
                rolUsuario.getId_rol_usuario(),
                rolUsuario.getId_rol().getId_rol(),
                rolUsuario.getId_rol().getNombre(),
                rolUsuario.getId_usuario().getId_usuario(),
                rolUsuario.getFecha_asignacion(),
                rolUsuario.getEstado()
        );
    }

    public static Rol_usuario actualizarDtoDomain(ModificarRolUsuarioDTO dto, Usuario usuario, Rol rol) {

        return new Rol_usuario(
                null,
                rol,
                usuario,
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarRamaDTO eliminarRolUsuarioDTODomain(Rol_usuario dto) {
        return new EliminarRamaDTO(
                dto.getId_rol_usuario()
        );
    }

}
