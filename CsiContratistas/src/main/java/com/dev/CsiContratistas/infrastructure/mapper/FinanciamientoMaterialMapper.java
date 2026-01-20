package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.CrearFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.EliminarFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.LeerFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.ModificarFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.EliminarFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.LeerFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.ModificarFinanciamientoMaterialDTO;

public class FinanciamientoMaterialMapper {
        public static Financiamiento_material crearDtoDomain(CrearFinanciamientoMaterialDTO dto, Financiamiento financiamiento, Material material) {

            return new Financiamiento_material(
                    null,
                    financiamiento,
                    material,
                    dto.getCantidad(),
                    dto.getPrecio_total()
        );
    }

    public static LeerFinanciamientoMaterialDTO leerDTOFinanciamientoMaterial(Financiamiento_material financiamientoMaterial) {
        return new LeerFinanciamientoMaterialDTO(
                financiamientoMaterial.getId_financiamiento_material(),
                financiamientoMaterial.getId_financiamiento().getId_financiamiento(),
                financiamientoMaterial.getId_material().getId_material(),
                financiamientoMaterial.getCantidad(),
                financiamientoMaterial.getPrecio_total()
                );
    }

    public static Financiamiento_material actualizarDtoDomain(ModificarFinanciamientoMaterialDTO dto, Financiamiento financiamiento, Material material) {

        return new Financiamiento_material(
                null,
                financiamiento,
                material,
                dto.getCantidad(),
                dto.getPrecio_total()
        );
    }

    public static EliminarFinanciamientoMaterialDTO eliminarFinanciamientoMaterialDTODomain(Financiamiento_material dto) {
        return new EliminarFinanciamientoMaterialDTO(
                dto.getId_financiamiento_material()
        );
    }

}
