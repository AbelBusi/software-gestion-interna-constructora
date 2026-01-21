package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol implements Serializable {

    private Integer id_rol;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}