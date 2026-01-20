package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.CrearFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.EliminarFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.LeerFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Financiamiento.ModificarFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.CrearFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.EliminarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.LeerFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.ModificarFormaTerrenoDTO;

public class FinanciamientoMapper {
        public static Financiamiento crearDtoDomain(CrearFinanciamientoDTO dto, Tipo_financiamiento tipoFinanciamiento, Cliente cliente, Obra obra) {

            return new Financiamiento(
                    null,
                    obra,
                    tipoFinanciamiento,
                    cliente,
                    dto.getCodigo_financiamiento(),
                    dto.getSub_total(),
                    dto.getIgv(),
                    dto.getTotal(),
                    dto.getFecha_financiamiento(),
                    dto.getForma_pago(),
                    dto.getEstado()
        );
    }

    public static LeerFinanciamientoDTO leerDTOFinanciamiento(Financiamiento financiamiento) {
        return new LeerFinanciamientoDTO(
                financiamiento.getId_financiamiento(),
                financiamiento.getId_obra().getId_obra(),
                financiamiento.getId_tipo_financiamiento().getId_tipo_financiamiento(),
                financiamiento.getId_cliente().getId_cliente(),
                financiamiento.getCodigo_financiamiento(),
                financiamiento.getSub_total(),
                financiamiento.getIgv(),
                financiamiento.getTotal(),
                financiamiento.getFecha_financiamiento(),
                financiamiento.getForma_pago(),
                financiamiento.getEstado()
        );
    }

    public static Financiamiento actualizarDtoDomain(ModificarFinanciamientoDTO dto,  Tipo_financiamiento tipoFinanciamiento, Cliente cliente, Obra obra) {

        return new Financiamiento(
                null,
                obra,
                tipoFinanciamiento,
                cliente,
                dto.getCodigo_financiamiento(),
                dto.getSub_total(),
                dto.getIgv(),
                dto.getTotal(),
                dto.getFecha_financiamiento(),
                dto.getForma_pago(),
                dto.getEstado()
        );
    }

    public static EliminarFinanciamientoDTO eliminarFinanciamientoDTODomain(Financiamiento dto) {
        return new EliminarFinanciamientoDTO(
                dto.getId_financiamiento()
        );
    }

}
