package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.CrearTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.EliminarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.LeerTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.ModificarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.CrearTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.EliminarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.ModificarTipoSueloDTO;

public class TipoSueloMapper {
        public static Tipo_suelo crearDtoDomain(CrearTipoSueloDTO dto) {

            return new Tipo_suelo(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static LeerTipoSueloDTO leerDTOTipoSuelo(Tipo_suelo tipoSuelo) {
        return new LeerTipoSueloDTO(
                tipoSuelo.getId_tipo_suelo(),
                tipoSuelo.getNombre(),
                tipoSuelo.getDescripcion(),
                tipoSuelo.getEstado()
        );
    }

    public static Tipo_suelo actualizarDtoDomain(ModificarTipoSueloDTO dto) {

        return new Tipo_suelo(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static EliminarTipoSueloDTO eliminarTipoSueloDTODomain(Tipo_suelo dto) {
        return new EliminarTipoSueloDTO(
                dto.getId_tipo_suelo()
        );
    }

}
