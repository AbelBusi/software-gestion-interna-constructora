package com.dev.CsiContratistas.infrastructure.dto.Rama;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarRamaDTO {

    private Integer id_profesion;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}