package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.CrearFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.EliminarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.LeerFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.ModificarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.CrearTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.EliminarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.ModificarTipoSueloDTO;

public class FormaTerrenoMapper {
        public static Forma_terreno crearDtoDomain(CrearFormaTerrenoDTO dto) {

            return new Forma_terreno(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static LeerFormaTerrenoDTO leerDTOFormaTerreno(Forma_terreno formaTerreno) {
        return new LeerFormaTerrenoDTO(
                formaTerreno.getId_forma_terreno(),
                formaTerreno.getNombre(),
                formaTerreno.getDescripcion(),
                formaTerreno.getEstado()
        );
    }

    public static Forma_terreno actualizarDtoDomain(ModificarFormaTerrenoDTO dto) {

        return new Forma_terreno(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static EliminarFormaTerrenoDTO eliminarFormaTerrenoDTODomain(Forma_terreno dto) {
        return new EliminarFormaTerrenoDTO(
                dto.getId_forma_terreno()
        );
    }

}
