package com.dev.CsiContratistas.infrastructure.dto.TipoObra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerTipoObraDTO {


    private Integer id_tipo_obra;
    private String nombre;
    private String descripcion;
    private Boolean estado;

}
