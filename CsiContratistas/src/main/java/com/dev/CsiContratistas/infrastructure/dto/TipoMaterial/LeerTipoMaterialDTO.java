package com.dev.CsiContratistas.infrastructure.dto.TipoMaterial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerTipoMaterialDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String clasificacion;
    private Boolean estado;

}
