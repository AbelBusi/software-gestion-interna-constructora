package com.dev.CsiContratistas.infrastructure.dto.Terreno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarTerrenoDTO {

    private Integer id_tipo_suelo;
    private Integer id_forma_terreno;
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