package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.CrearCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.EliminarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.LeerCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.ModificarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.CrearModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.EliminarModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.LeerModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.ModificarModeloObraDTO;

public class ModeloObraMapper {

    public static Modelo_obra crearDtoDomain(CrearModeloObraDTO dto,Tipo_obra tipoObra) {

        return new Modelo_obra(
                null,
                tipoObra,
                dto.getUrl_modelo3d(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getUso_destinado(),
                dto.getPisos(),
                dto.getAmbientes(),
                dto.getArea_total(),
                dto.getCapacidad_personas(),
                dto.getPrecio_preferencial(),
                dto.getEstado()
        );

    }

    public static LeerModeloObraDTO leerDTOModeloObra(Modelo_obra modeloObra) {
        return new LeerModeloObraDTO(

                modeloObra.getId_modelo_obra(),
                modeloObra.getId_tipo_obra().getId_tipo_obra(),
                modeloObra.getUrl_modelo3d(),
                modeloObra.getNombre(),
                modeloObra.getDescripcion(),
                modeloObra.getUso_destinado(),
                modeloObra.getPisos(),
                modeloObra.getAmbientes(),
                modeloObra.getArea_total(),
                modeloObra.getCapacidad_personas(),
                modeloObra.getPrecio_preferencial(),
                modeloObra.getEstado()

        );
    }

    public static Modelo_obra actualizarDtoDomain(ModificarModeloObraDTO dto, Tipo_obra tipoObra) {

        return new Modelo_obra(
                null,
                tipoObra,
                dto.getUrl_modelo3d(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getUso_destinado(),
                dto.getPisos(),
                dto.getAmbientes(),
                dto.getArea_total(),
                dto.getCapacidad_personas(),
                dto.getPrecio_preferencial(),
                dto.getEstado()
        );

    }

    public static EliminarModeloObraDTO eliminarModeloObraDTODomain(Modelo_obra dto) {
        return new EliminarModeloObraDTO(
                dto.getId_modelo_obra()
        );
    }

}