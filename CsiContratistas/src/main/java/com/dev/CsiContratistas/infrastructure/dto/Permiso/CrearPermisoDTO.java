package com.dev.CsiContratistas.infrastructure.dto.Permiso;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearPermisoDTO {

    @NotBlank(message = "el nombre no puede repetirse")
    private String nombre;

    private String descripcion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;



}
