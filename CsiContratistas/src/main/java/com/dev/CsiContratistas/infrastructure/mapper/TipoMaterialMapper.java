package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.EliminarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.ModificarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.CrearTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.LeerTipoMaterialDTO;

public class TipoMaterialMapper {

    public static Tipo_material crearDtoDomain(CrearTipoMaterialDTO dto) {

        return new Tipo_material(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getClasificacion(),
                dto.getEstado()
        );
    }

    public static LeerTipoMaterialDTO leerDTOtipoMaterial(Tipo_material tipoMaterial) {
        return new LeerTipoMaterialDTO(
                tipoMaterial.getIdtipomaterial(),
                tipoMaterial.getNombre(),
                tipoMaterial.getDescripcion(),
                tipoMaterial.getClasificacion(),
                tipoMaterial.getEstado()
        );
    }

    public static Tipo_material actualizarDtoDomain(ModificarTipoMaterialDTO dto) {

        return new Tipo_material(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getClasificacion(),
                dto.getEstado()
        );
    }

    public static EliminarTipoMaterialDTO eliminarDtoDomain(Tipo_material dto) {
        return new EliminarTipoMaterialDTO(
                dto.getIdtipomaterial()
        );
    }
}