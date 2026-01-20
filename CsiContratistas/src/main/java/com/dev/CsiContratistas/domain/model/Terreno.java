package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Terreno implements Serializable {

    private Integer id_terreno;
    private Tipo_suelo id_tipo_suelo;
    private Forma_terreno id_forma_terreno;
    private BigDecimal area_total;
    private BigDecimal area_util;
    private BigDecimal frente_metros;
    private BigDecimal fondo_metros;
    private String zonificacion;
    private Boolean servicios_basicos;
    private String acceso_vial;
    private String observaciones;
    private String coordenadas;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}