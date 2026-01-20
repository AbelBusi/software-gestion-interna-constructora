package com.dev.CsiContratistas.infrastructure.dto.FormaTerreno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearFormaTerrenoDTO {

    private String nombre;
    private String descripcion;
    private Boolean estado;

}
