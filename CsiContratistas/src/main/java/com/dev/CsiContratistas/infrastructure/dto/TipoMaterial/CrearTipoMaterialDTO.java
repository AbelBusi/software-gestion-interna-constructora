package com.dev.CsiContratistas.infrastructure.dto.TipoMaterial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearTipoMaterialDTO {

    @NotBlank(message = "el nombre no puede repetirse")
    private String nombre;

    private String descripcion;

    private String clasificacion;
    private Boolean estado = true;



}
