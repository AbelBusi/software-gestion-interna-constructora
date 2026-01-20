package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.dto.Rol.CrearRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.EliminarRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.LeerRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rol.ModificarRolDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.ModificarTipoMaterialDTO;

public class RolMapper {

    public static Rol crearDtoDomain(CrearRolDTO dto) {

        return new Rol(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerRolDTO leerDTORol(Rol rol) {
        return new LeerRolDTO(
                rol.getId_rol(),
                rol.getNombre(),
                rol.getDescripcion(),
                rol.getFecha_asignacion(),
                rol.getEstado()
        );
    }

    public static Rol actualizarDtoDomain(ModificarRolDTO dto) {

        return new Rol(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarRolDTO eliminarRolDTODomain(Rol dto) {
        return new EliminarRolDTO(
                dto.getId_rol()
        );
    }
}