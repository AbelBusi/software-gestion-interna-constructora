package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Obra.CrearObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Obra.EliminarObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Obra.LeerObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Obra.ModificarObraDTO;

public class ObraMapper {

    public static Obra crearDtoDomain(CrearObraDTO dto, Empleado responsable,
                                      Modelo_obra modeloObra,
                                      Terreno terreno, Tipo_construccion tipoConstruccion) {

        return new Obra(
                null,
                responsable,
                modeloObra,
                terreno,
                tipoConstruccion,
                dto.getFecha_inicio(),
                dto.getFecha_finalizacion(),
                dto.getEstado(),
                dto.getNombre()
        );

    }

    public static LeerObraDTO leerDTOObra(Obra obra) {
        return new LeerObraDTO(

                obra.getId_obra(),
                obra.getId_responsable().getId_empleado(),
                obra.getId_modelo_obra().getId_modelo_obra(),
                obra.getId_terreno().getId_terreno(),
                obra.getId_tipo_construccion().getId_tipo_construccion(),
                obra.getFecha_inicio(),
                obra.getFecha_finalizacion(),
                obra.getEstado(),
                obra.getNombre()


        );
    }

    public static Obra actualizarDtoDomain(ModificarObraDTO dto, Empleado responsable,
                                           Modelo_obra modeloObra,
                                           Terreno terreno, Tipo_construccion tipoConstruccion) {

        return new Obra(
                null,
                responsable,
                modeloObra,
                terreno,
                tipoConstruccion,
                dto.getFecha_inicio(),
                dto.getFecha_finalizacion(),
                dto.getEstado(),
                dto.getNombre()
        );

    }

    public static EliminarObraDTO eliminarObraDTODomain(Obra dto) {
        return new EliminarObraDTO(
                dto.getId_obra()
        );
    }

}