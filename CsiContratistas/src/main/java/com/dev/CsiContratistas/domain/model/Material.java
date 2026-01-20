package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Material implements Serializable {

    private Integer id_material;
    private Tipo_material Tipo_material;
    private String nombre;
    private BigDecimal precio_unitario;
    private Integer stock_actual;
    private BigDecimal masa_especifica;
    private BigDecimal longitud;
    private BigDecimal temperatura_termodinamica;
    private String descripcion;
    private Boolean estado;

}