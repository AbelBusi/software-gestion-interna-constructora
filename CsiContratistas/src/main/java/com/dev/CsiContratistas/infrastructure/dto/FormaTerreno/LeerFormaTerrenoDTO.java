package com.dev.CsiContratistas.infrastructure.dto.FormaTerreno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerFormaTerrenoDTO {

    private Integer id_forma_terreno;
    private String nombre;
    private String descripcion;
    private Boolean estado;

}