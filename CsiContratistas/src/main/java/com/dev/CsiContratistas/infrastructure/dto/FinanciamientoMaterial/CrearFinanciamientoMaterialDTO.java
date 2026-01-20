package com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearFinanciamientoMaterialDTO {

    private Integer id_financiamiento;
    private Integer id_material;
    private Integer cantidad;
    private BigDecimal precio_total;

}
