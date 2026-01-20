package com.dev.CsiContratistas.infrastructure.dto.Material;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearMaterialDTO {

    private Integer tipo_material;
    private String nombre;
    private BigDecimal precio_unitario;
    private Integer stock_actual;
    private BigDecimal masa_especifica;
    private BigDecimal longitud;
    private BigDecimal temperatura_termodinamica;
    private String descripcion;
    private Boolean estado;

}
