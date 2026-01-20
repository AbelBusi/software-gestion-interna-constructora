package com.dev.CsiContratistas.infrastructure.dto.TipoObra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarTipoObraDTO {

    private String nombre;
    private String descripcion;
    private Boolean estado;
}