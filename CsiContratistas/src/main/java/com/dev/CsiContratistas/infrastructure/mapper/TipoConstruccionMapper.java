package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.CrearTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.EliminarTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.LeerTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.ModificarTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.CrearTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.EliminarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.ModificarTipoSueloDTO;

public class TipoConstruccionMapper {
        public static Tipo_construccion crearDtoDomain(CrearTipoConstruccionDTO dto) {

            return new Tipo_construccion(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static LeerTipoConstruccionDTO leerDTOTipoConstruccion(Tipo_construccion tipoConstruccion) {
            return new LeerTipoConstruccionDTO(
                tipoConstruccion.getId_tipo_construccion(),
                tipoConstruccion.getNombre(),
                tipoConstruccion.getDescripcion(),
                tipoConstruccion.getEstado()
        );
    }

    public static Tipo_construccion actualizarDtoDomain(ModificarTipoConstruccionDTO dto) {

        return new Tipo_construccion(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static EliminarTipoConstruccionDTO eliminarTipoConstruccionDTODomain(Tipo_construccion dto) {
        return new EliminarTipoConstruccionDTO(
                dto.getId_tipo_construccion()
        );
    }

}