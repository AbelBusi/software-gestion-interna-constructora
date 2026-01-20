package com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerTipoFinanciamientoDTO {


    private Integer id_tipo_financiamiento;
    private String nombre;
    private String descripcion;
    private Boolean estado;

}
