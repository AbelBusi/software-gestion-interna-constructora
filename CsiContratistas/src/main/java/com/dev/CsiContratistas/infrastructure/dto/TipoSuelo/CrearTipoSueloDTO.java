package com.dev.CsiContratistas.infrastructure.dto.TipoSuelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearTipoSueloDTO {

    private String nombre;
    private String descripcion;
    private Boolean estado;

}
