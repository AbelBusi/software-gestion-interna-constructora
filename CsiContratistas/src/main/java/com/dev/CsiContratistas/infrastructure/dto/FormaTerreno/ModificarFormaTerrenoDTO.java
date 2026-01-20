package com.dev.CsiContratistas.infrastructure.dto.FormaTerreno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarFormaTerrenoDTO {

    private String nombre;
    private String descripcion;
    private Boolean estado;
}