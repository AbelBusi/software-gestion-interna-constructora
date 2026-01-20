package com.dev.CsiContratistas.infrastructure.dto.Material;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerMaterialDTO {

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
