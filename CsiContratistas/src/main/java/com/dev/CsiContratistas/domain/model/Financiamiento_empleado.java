package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Financiamiento_empleado {


    private Integer id_financiamiento_empleado;
    private Financiamiento id_financiamiento;
    private Empleado id_empleado;
    private Integer dias_participacion;
    private BigDecimal costo_total;

}
