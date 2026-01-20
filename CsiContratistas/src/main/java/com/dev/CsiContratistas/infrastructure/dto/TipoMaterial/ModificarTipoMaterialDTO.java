package com.dev.CsiContratistas.infrastructure.dto.TipoMaterial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarTipoMaterialDTO {

    private String nombre;
    private String descripcion;
    private String clasificacion;
    private Boolean estado;

}