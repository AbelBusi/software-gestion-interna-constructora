package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.CrearFinanciamientoEmpleadolDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.EliminarFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.LeerFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.ModificarFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.EliminarFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.LeerFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.ModificarFinanciamientoMaterialDTO;

public class FinanciamientoEmpleadoMapper {
        public static Financiamiento_empleado crearDtoDomain(CrearFinanciamientoEmpleadolDTO dto, Financiamiento financiamiento, Empleado empleado) {

            return new Financiamiento_empleado(
                    null,
                    financiamiento,
                    empleado,
                    dto.getDias_participacion(),
                    dto.getCosto_total()
        );
    }

    public static LeerFinanciamientoEmpleadoDTO leerDTOFinanciamientoEmpleado(Financiamiento_empleado financiamientoEmpleado) {
        return new LeerFinanciamientoEmpleadoDTO(
                financiamientoEmpleado.getId_financiamiento_empleado(),
                financiamientoEmpleado.getId_financiamiento().getId_financiamiento(),
                financiamientoEmpleado.getId_empleado().getId_empleado(),
                financiamientoEmpleado.getDias_participacion(),
                financiamientoEmpleado.getCosto_total()
                );
    }

    public static Financiamiento_empleado actualizarDtoDomain(ModificarFinanciamientoEmpleadoDTO dto, Financiamiento financiamiento, Empleado empleado) {

        return new Financiamiento_empleado(
                null,
                financiamiento,
                empleado,
                dto.getDias_participacion(),
                dto.getCosto_total()
        );
    }

    public static EliminarFinanciamientoEmpleadoDTO eliminarFinanciamientoEmpleadoDTODomain(Financiamiento_empleado dto) {
        return new EliminarFinanciamientoEmpleadoDTO(
                dto.getId_financiamiento_empleado()
        );
    }

}
