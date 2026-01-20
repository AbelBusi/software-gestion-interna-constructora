package com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarTipoConstruccionDTO {

    private String nombre;
    private String descripcion;
    private Boolean estado;
}