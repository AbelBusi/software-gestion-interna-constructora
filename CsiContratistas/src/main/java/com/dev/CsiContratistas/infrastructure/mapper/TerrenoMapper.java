package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Rama.CrearRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.EliminarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.LeerRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Rama.ModificarRamaDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.CrearTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.EliminarTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.LeerTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.ModificarTerrenoDTO;

public class TerrenoMapper {
        public static Terreno crearDtoDomain(CrearTerrenoDTO dto, Tipo_suelo tipoSuelo, Forma_terreno formaTerreno) {

        return new Terreno(
                null,
                tipoSuelo,
                formaTerreno,
                dto.getArea_total(),
                dto.getArea_util(),
                dto.getFrente_metros(),
                dto.getFondo_metros(),
                dto.getZonificacion(),
                dto.getServicios_basicos(),
                dto.getAcceso_vial(),
                dto.getObservaciones(),
                dto.getCoordenadas(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static LeerTerrenoDTO leerDTOTerreno(Terreno terreno) {
        return new LeerTerrenoDTO(
                terreno.getId_terreno(),
                terreno.getId_tipo_suelo().getId_tipo_suelo(),
                terreno.getId_forma_terreno().getId_forma_terreno(),
                terreno.getArea_total(),
                terreno.getArea_util(),
                terreno.getFrente_metros(),
                terreno.getFondo_metros(),
                terreno.getZonificacion(),
                terreno.getServicios_basicos(),
                terreno.getAcceso_vial(),
                terreno.getObservaciones(),
                terreno.getCoordenadas(),
                terreno.getFecha_asignacion(),
                terreno.getEstado()
        );
    }

    public static Terreno actualizarDtoDomain(ModificarTerrenoDTO dto, Tipo_suelo tipoSuelo, Forma_terreno formaTerreno) {

        return new Terreno(
                null,
                tipoSuelo,
                formaTerreno,
                dto.getArea_total(),
                dto.getArea_util(),
                dto.getFrente_metros(),
                dto.getFondo_metros(),
                dto.getZonificacion(),
                dto.getServicios_basicos(),
                dto.getAcceso_vial(),
                dto.getObservaciones(),
                dto.getCoordenadas(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );
    }

    public static EliminarTerrenoDTO eliminarTerrenoDTODomain(Terreno dto) {
        return new EliminarTerrenoDTO(
                dto.getId_terreno()
        );
    }

}
