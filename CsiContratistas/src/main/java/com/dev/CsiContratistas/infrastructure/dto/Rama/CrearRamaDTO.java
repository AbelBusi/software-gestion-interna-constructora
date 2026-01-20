package com.dev.CsiContratistas.infrastructure.dto.Rama;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearRamaDTO {

    private Integer id_profesion;

    @NotBlank(message = "el nombre no puede repetirse")
    private String nombre;

    private String descripcion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}
