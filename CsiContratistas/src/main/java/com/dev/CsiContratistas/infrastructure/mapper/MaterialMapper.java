package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.dto.Material.CrearMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.EliminarMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.LeerMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.ModificarMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.EliminarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;

public class MaterialMapper {
        public static Material crearDtoDomain(CrearMaterialDTO dto, Tipo_material tipoMaterial) {

        return new Material(
                null,
                tipoMaterial,
                dto.getNombre(),
                dto.getPrecio_unitario(),
                dto.getStock_actual(),
                dto.getMasa_especifica(),
                dto.getLongitud(),
                dto.getTemperatura_termodinamica(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static LeerMaterialDTO leerDTOMaterial(Material material) {
        return new LeerMaterialDTO(
                material.getId_material(),
                material.getTipo_material(),
                material.getNombre(),
                material.getPrecio_unitario(),
                material.getStock_actual(),
                material.getMasa_especifica(),
                material.getLongitud(),
                material.getTemperatura_termodinamica(),
                material.getDescripcion(),
                material.getEstado()
        );
    }

    public static Material actualizarDtoDomain(ModificarMaterialDTO dto,Tipo_material tipoMaterial) {

        return new Material(
                null,
                tipoMaterial,
                dto.getNombre(),
                dto.getPrecio_unitario(),
                dto.getStock_actual(),
                dto.getMasa_especifica(),
                dto.getLongitud(),
                dto.getTemperatura_termodinamica(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static EliminarMaterialDTO eliminarMaterialDTODomain(Material dto) {
        return new EliminarMaterialDTO(
                dto.getId_material()
        );
    }

}
