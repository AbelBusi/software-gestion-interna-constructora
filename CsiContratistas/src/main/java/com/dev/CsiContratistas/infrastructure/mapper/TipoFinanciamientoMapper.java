package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.CrearTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.EliminarTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.LeerTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.ModificarTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.CrearTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.EliminarTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.LeerTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.ModificarTipoFinanciamientoDTO;

public class TipoFinanciamientoMapper {
        public static Tipo_financiamiento crearDtoDomain(CrearTipoFinanciamientoDTO dto) {

            return new Tipo_financiamiento(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static LeerTipoFinanciamientoDTO leerDTOTipoFinanciamiento(Tipo_financiamiento tipoFinanciamiento) {
            return new LeerTipoFinanciamientoDTO(
                tipoFinanciamiento.getId_tipo_financiamiento(),
                tipoFinanciamiento.getNombre(),
                tipoFinanciamiento.getDescripcion(),
                tipoFinanciamiento.getEstado()
        );
    }

    public static Tipo_financiamiento actualizarDtoDomain(ModificarTipoFinanciamientoDTO dto) {

        return new Tipo_financiamiento(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static EliminarTipoFinanciamientoDTO eliminarTipoFinanciamientoDTODomain(Tipo_financiamiento dto) {
        return new EliminarTipoFinanciamientoDTO(
                dto.getId_tipo_financiamiento()
        );
    }

}