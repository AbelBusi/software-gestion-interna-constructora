package com.dev.CsiContratistas.infrastructure.dto.ModeloObra;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerModeloObraDTO {

    private Integer id_modelo_obra;
    private Integer id_tipo_obra;
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
