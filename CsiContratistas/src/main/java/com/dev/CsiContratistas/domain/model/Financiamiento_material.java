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
public class Financiamiento_material {

    private Integer id_financiamiento_material;
    private Financiamiento id_financiamiento;
    private Material id_material;
    private Integer cantidad;
    private BigDecimal precio_total;

}
