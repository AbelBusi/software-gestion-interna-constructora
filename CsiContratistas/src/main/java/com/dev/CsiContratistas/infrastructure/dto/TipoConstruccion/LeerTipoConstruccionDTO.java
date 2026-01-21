package com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerTipoConstruccionDTO {


    private Integer id_tipo_construccion;
    private String nombre;
    private String descripcion;
    private Boolean estado;

}
