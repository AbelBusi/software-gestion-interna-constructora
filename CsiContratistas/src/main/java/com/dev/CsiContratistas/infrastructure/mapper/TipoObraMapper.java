package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.CrearPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.EliminarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.LeerPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Permiso.ModificarPermisoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.CrearTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.EliminarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.LeerTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.ModificarTipoObraDTO;

public class TipoObraMapper {
        public static Tipo_obra crearDtoDomain(CrearTipoObraDTO dto) {

        return new Tipo_obra(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static LeerTipoObraDTO leerDTOTipoPrueba(Tipo_obra tipoObra) {
        return new LeerTipoObraDTO(
                tipoObra.getId_tipo_obra(),
                tipoObra.getNombre(),
                tipoObra.getDescripcion(),
                tipoObra.getEstado()
        );
    }

    public static Tipo_obra actualizarDtoDomain(ModificarTipoObraDTO dto) {

        return new Tipo_obra(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getEstado()
        );
    }

    public static EliminarTipoObraDTO eliminarTipoObraDTODomain(Tipo_obra dto) {
        return new EliminarTipoObraDTO(
                dto.getId_tipo_obra()
        );
    }

}
