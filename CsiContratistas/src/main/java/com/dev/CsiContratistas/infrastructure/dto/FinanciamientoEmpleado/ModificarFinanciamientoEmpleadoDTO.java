package com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarFinanciamientoEmpleadoDTO {

    private Integer id_financiamiento;
    private Integer id_empleado;
    private Integer dias_participacion;
    private BigDecimal costo_total;

}