package com.dev.CsiContratistas.infrastructure.dto.Material;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarMaterialDTO {

    private Integer Tipo_material;
    private String nombre;
    private BigDecimal precio_unitario;
    private Integer stock_actual;
    private BigDecimal masa_especifica;
    private BigDecimal longitud;
    private BigDecimal temperatura_termodinamica;
    private String descripcion;
    private Boolean estado;

}