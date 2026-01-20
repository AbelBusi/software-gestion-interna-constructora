package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Modelo_obra {

    private Integer id_modelo_obra;
    private Tipo_obra id_tipo_obra;
    private String url_modelo3d;
    private String nombre;
    private String descripcion;
    private String uso_destinado;
    private Integer pisos;
    private Integer ambientes;
    private BigDecimal area_total;
    private Integer capacidad_personas;
    private BigDecimal precio_preferencial;
    private Boolean estado;

}